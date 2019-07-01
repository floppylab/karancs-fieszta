<template>
    <div>
        <div id="animations">
            <div class="animation" v-for="animation in animations.content" :key="animation.id">
                <animation-preview @dblclick.native="heartAnimation(animation)" :animation="animation"
                                   :class="{ 'pointer-cursor' : loggedIn }"></animation-preview>
                <div class="meta">
                    <p @click="heartAnimation(animation)"
                       :class="{ 'pointer-cursor' : loggedIn, 'default-cursor' : owned(animation) }">
                        <font-awesome-icon icon="heart" :style="{ color: hearted(animation) ? 'red' : '#ffd152' }"/>
                        {{ animation.hearts.length }}
                    </p>
                    <div>
                        <p class="pointer-cursor" v-if="owned(animation)">
                            <router-link :to="'/editor?id=' + animation.id">
                                <font-awesome-icon icon="pencil-alt"/>
                            </router-link>
                        </p>
                        <p @click="deleteAnimation(animation)" class="pointer-cursor" v-if="owned(animation)">
                            <font-awesome-icon icon="trash-alt"/>
                        </p>
                        <p v-if="!owned(animation)">{{ animation.createdBy }}</p>
                    </div>
                </div>
                <h3>{{ animation.title }}</h3>
            </div>
        </div>
        <div class="pagination">
            <span @click="getAnimations(0, size)" v-if="!animations.first">
                <font-awesome-icon icon="angle-double-left" :style="{ color: 'ivory' }"/>
            </span>
            <span @click="getAnimations(animations.number - 1, size)" v-if="!animations.first">
                <font-awesome-icon icon="angle-left" :style="{ color: 'ivory' }"/>
            </span>
            <span @click="getAnimations(animations.number - 1, size)" v-if="!animations.first">{{ animations.number  }}</span>
            <span>{{ animations.number + 1 }}</span>
            <span @click="getAnimations(animations.number + 1, size)" v-if="!animations.last">{{ animations.number + 2 }}</span>
            <span @click="getAnimations(animations.number + 1, size)" v-if="!animations.last">
                <font-awesome-icon icon="angle-right" :style="{ color: 'ivory' }"/>
            </span>
            <span @click="getAnimations(animations.totalPages - 1, size)" v-if="!animations.last">
                <font-awesome-icon icon="angle-double-right" :style="{ color: 'ivory' }"/>
            </span>
        </div>
    </div>
</template>

<script>
  import {api} from '../api'
  import AnimationPreview from "./AnimationPreview";

  export default {
    name: 'AnimationLister',
    components: {AnimationPreview},
    data: () => {
      return {
        animations: [],
        size: 6
      }
    },
    computed: {
      loggedIn() {
        return this.$store.getters.isLoggedIn
      }
    },
    created() {
      this.getAnimations(0, this.size)
    },
    methods: {
      getAnimations: function (page, size) {
        api.get('/animations', {params: {size: size, page: page}})
          .then(function (response) {
            console.log(response.data)
            this.animations = response.data
          }.bind(this))
        console.log(this.animations.number)
      },
      hearted: function (animation) {
        if (this.$store.getters.isLoggedIn) {
          return animation.hearts.includes(this.$store.getters.username)
        }
        return false
      },
      owned: function (animation) {
        if (this.$store.getters.isLoggedIn) {
          return animation.createdBy === this.$store.getters.username
        }
        return false
      },
      heartAnimation: function (animation) {
        if (!this.$store.getters.isLoggedIn || this.owned(animation)) return;
        let promise = null;
        if (!this.hearted(animation)) {
          promise = api.post(`/animations/${animation.id}/hearts`)
        } else {
          promise = api.delete(`/animations/${animation.id}/hearts`)
        }
        promise
          .then((response) => {
            this.getAnimations(this.animations.number, this.size)
          })
      },
      deleteAnimation: function (animation) {
        let options = {
          html: true,
          loader: false,
          reverse: false,
          okText: 'igen',
          cancelText: 'nem',
          backdropClose: true
        };
        this.$dialog
          .confirm('<h2>megerősítés</h2><p>biztosan törlöd?</p>', options)
          .then(function (dialog) {
            api.delete(`/animations/${animation.id}`)
              .then(function (response) {
                this.getAnimations(this.animations.number, this.size)
              }.bind(this))
          }.bind(this))
      }
    }
  }
</script>

<style scoped>

    #animations {
        display: flex;
        flex-wrap: wrap;
    }

    #animations > div {
        flex: 1 0 27%;
        margin: 40px 20px;
    }

    #animations h3 {
        text-transform: lowercase;
    }

    .meta {
        width: 185px;
        margin: 0 auto;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .meta div {
        display: flex;
    }

    .meta div p {
        margin-left: 15px;
    }

    .pagination {
        display: flex;
        justify-content: center;
    }

    .pagination span {
        padding: 15px;
    }

    .pointer-cursor {
        cursor: pointer;
    }

    .default-cursor {
        cursor: default;
    }

</style>
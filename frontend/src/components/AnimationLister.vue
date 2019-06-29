<template>
    <div id="animation-lister">
        <div class="animation-row" v-for="animation in animations.content">
            <div class="animation">
                <div>mini-animation - canvas? or pixels?</div>
                <h3>{{ animation.title }}</h3>
                <p>heart icon {{ animation.hearts.length }} {{ animation.hearts }}</p>
            </div>
        </div>
    </div>
</template>

<script>
    import {api} from '../api'

    export default {
    name: 'AnimationLister',
    data: () => {
      return {
        animations: []
      }
    },
    created () {
      api.get('/animations')
        .then((response) => {
          console.log(response)
          this.animations = response
        })
    }
  }
</script>

<style scoped>

</style>
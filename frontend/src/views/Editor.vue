<template>
    <div @mousedown="setLeftButtonState" @mouseup="setLeftButtonState">
        <the-header></the-header>
        <div v-if="this.frames.length > 0" class="editor">
            <div >
                <span @click="previousFrame" v-show="current > 0">
                    <font-awesome-icon icon="angle-left" size="2x" :style="{ color: 'ivory' }"/>
                </span>
                <span @click="firstFrame" v-show="current > 0">
                    <font-awesome-icon icon="angle-double-left" size="2x" :style="{ color: 'ivory' }"/>
                </span>
            </div>
            <div>
                <div class="input-row">
                    <p>
                        <label>cím *</label>
                        <input type="text" v-model="title"/>
                    </p>
                </div>
                <div class="input-row">
                    <p>
                        <label>hossz (ms) *</label>
                        <input type="number" v-model="frames[current].time" step="500" min="0"/>
                    </p>
                    <p>
                        <label>animáció *</label>
                        <select v-model="frames[current].animation">
                            <option value="random">mozaikszerű</option>
                            <option value="topdown">fentről le</option>
                            <option value="bottomup">lentről fel</option>
                        </select>
                    </p>
                </div>
                <p>{{ current + 1 }} / {{ frames.length }}</p>
                <div id="hotel" v-if="frames[current].data">
                    <div v-for="(floor, floorIndex) in frames[current].data" :key="floorIndex" class="floor">
                        <div v-for="(room, roomIndex) in floor" :key="roomIndex" class="room" @mouseover.passive='check(floorIndex, roomIndex)'>
                            <input :id="'r' + floorIndex + roomIndex" type="checkbox"
                                   v-model="frames[current].data[floorIndex][roomIndex]"/>
                            <label :for="'r' + floorIndex + roomIndex"></label>
                        </div>
                    </div>
                </div>
            </div>
            <div >
                <span @click="nextFrame" v-show="current < (frames.length -1)">
                    <font-awesome-icon icon="angle-right" size="2x":style="{ color: 'ivory' }"/>
                </span>
                <span @click="lastFrame" v-show="current < (frames.length -1)">
                    <font-awesome-icon icon="angle-double-right" size="2x":style="{ color: 'ivory' }"/>
                </span>
            </div>
        </div>

        <div class="button-row">
            <span @click="addFrame" class="like-a" title="új hozzáadása">
                <font-awesome-icon icon="plus" size="2x"/>
            </span>
            <span @click="copyPrevious" class="like-a" :class="{ disabled : current < 1 }" title="előző másolása">
                <font-awesome-icon icon="copy" size="2x"/>
            </span>
            <span @click="clearFrame" class="like-a" title="aktuális alapállapotba">
                <font-awesome-icon icon="sync-alt" size="2x"/>
            </span>
            <span @click="removeFrame" class="like-a" title="aktuális törlése" :class="{ disabled : frames.length <= 1 }">
                <font-awesome-icon icon="trash-alt" size="2x"/>
            </span>
            <span @click="closeEditor" class="like-a" title="bezárás mentés nélkül">
                <font-awesome-icon icon="door-closed" size="2x"/>
            </span>
            <span @click="saveAnimation" class="like-a" title="mentés" :class="{ disabled : dataMissing }">
                <font-awesome-icon icon="save" size="2x"/>
            </span>
        </div>
        <the-footer></the-footer>
    </div>
</template>

<script>

  import {api} from '../api'
  import {parseAnimation, stringifyAnimation} from '../functions'
  import TheHeader from "../components/TheHeader";
  import TheFooter from "../components/TheFooter";

  export default {
    name: 'editor',
    components: {TheFooter, TheHeader},
    data: () => {
      return {
        current: 0,
        id: null,
        title: '',
        frames: [{
          time: 1000,
          animation: 'bottomup',
          // data: new Array(7).fill(new Array(8).fill(false))
          data: [
            [false, false, false, false, false, false, false, false],
            [false, false, false, false, false, false, false, false],
            [false, false, false, false, false, false, false, false],
            [false, false, false, false, false, false, false, false],
            [false, false, false, false, false, false, false, false],
            [false, false, false, false, false, false, false, false],
            [false, false, false, false, false, false, false, false]
          ]
        }],

        leftMouseButtonOnlyDown: false
      }
    },
    computed: {
      dataMissing () {
        return this.title.trim().length < 1 || this.frames.some(frame => frame.time < 0 || frame.animation.trim().length < 1)
      }
    },
    methods: {
      addFrame() {
        this.frames.splice(this.current + 1, 0, {
          time: 1000,
          animation: 'cut',
          // data: new Array(7).fill(new Array(8).fill(false))
          data: [
            [false, false, false, false, false, false, false, false],
            [false, false, false, false, false, false, false, false],
            [false, false, false, false, false, false, false, false],
            [false, false, false, false, false, false, false, false],
            [false, false, false, false, false, false, false, false],
            [false, false, false, false, false, false, false, false],
            [false, false, false, false, false, false, false, false]
          ]
        })
        if (this.frames.length !== 1) this.current++
      },
      removeFrame() {
        if (this.frames.length <= 1) return
        this.frames.splice(this.current, 1)
        if (this.current > 0) this.current--
      },
      clearFrame() {
        this.frames[this.current].data = [
          [false, false, false, false, false, false, false, false],
          [false, false, false, false, false, false, false, false],
          [false, false, false, false, false, false, false, false],
          [false, false, false, false, false, false, false, false],
          [false, false, false, false, false, false, false, false],
          [false, false, false, false, false, false, false, false],
          [false, false, false, false, false, false, false, false]
        ]
      },
      check (floor, room) {
        console.log(this.leftMouseButtonOnlyDown)
        if (this.leftMouseButtonOnlyDown) {
          this.frames[this.current].data[floor][room] = !this.frames[this.current].data[floor][room]
        }
      },
      setLeftButtonState (e) {
        this.leftMouseButtonOnlyDown = e.buttons === undefined
          ? e.which === 1
          : (e.buttons & 1) === 1
      },
      previousFrame() {
        this.current--
      },
      nextFrame() {
        this.current++
      },
      firstFrame() {
        this.current = 0
      },
      lastFrame() {
        this.current = this.frames.length - 1
      },
      saveAnimation() {
        let promise = null
        if (this.id) {
          promise = api.put(`/animations/${this.id}`, {title: this.title, code: stringifyAnimation(this.frames)})
        } else {
          promise = api.post('/animations', {title: this.title, code: stringifyAnimation(this.frames)})
        }
        promise
          .then((response) => {
            console.log(response)
            let options = {
              loader: false,
              reverse: false,
              okText: 'rendben!',
              backdropClose: true
            }
            this.$dialog.alert('adatok mentve!', options).then(function(dialog) {
              console.log('bezárva')
            })
          })
      },
      loadAnimation (id) {
        api.get(`/animations/${id}`)
          .then((response) => {
            console.log(response.data)
            this.id = response.data.id
            this.current = 0
            this.title = response.data.title
            this.frames = parseAnimation(response.data.code)
          })
      },
      copyAnimation (id) {
        api.get(`/animations/${id}`)
          .then((response) => {
            console.log(response.data)
            this.current = 0
            this.title = response.data.title
            this.frames = parseAnimation(response.data.code)
          })
      },
      copyPrevious () {
        if (this.current == 0) return
        this.frames[this.current].data = JSON.parse(JSON.stringify(this.frames[this.current - 1].data))
      },
      closeEditor () {
        let options = {
          html: true,
          loader: false,
          reverse: false,
          okText: 'igen',
          cancelText: 'nem',
          backdropClose: true
        }
        this.$dialog
          .confirm('<h2>megerősítés</h2><p>biztosan bezárod?</p><p>a nem mentett adatok elvesznek!</p>', options)
          .then(function (dialog) {
            this.$router.push('/')
          }.bind(this))
      }
    },
    created() {
      if (this.$route.query.id) {
        this.loadAnimation(this.$route.query.id)
      } else if (this.$route.query.copy) {
        this.copyAnimation(this.$route.query.copy)
      }
    }
  }
</script>
<style>
    .floor {
        display: flex;
        margin: 0 auto;
        justify-content: center;
        align-items: center;
    }

    .room {
        margin: 10px;
    }

    .room div {
        width: 100%;
        height: 100%;
    }

    #hotel {
        min-height: 350px;
    }

    #hotel label {
        display: block;
        border: solid 1px #333;
        width: 5vw;
        max-width: 60px;
        height: 5vh;
        max-height: 30px;
        color: gray;
        text-align: center;
    }

    #hotel input[type=checkbox] {
        display: none;
    }

    #hotel input:checked + label {
        background-color: ivory;
    }

    .editor {
        display: flex;
        justify-content: space-evenly;
        align-items: center;
        max-width: 1000px;
        margin: 0 auto;
    }

    .editor > div:first-child, .editor > div:last-child {
        flex: 1;
        min-width: 50px;
        justify-content: space-around;
        flex-direction: column;
    }

    .editor > div > span {
        display: block;
        margin: 30px 0;
    }

    .editor div:not(:first-child):not(:last-child) {
        flex: 3;
    }

    .input-row {
        display: flex;
        text-align: left;
        flex-wrap: wrap;
    }

    .input-row input {
        width: 25%;
    }

    .input-row p {
        flex: 1;
        display: flex;
    }

    .input-row input, .input-row select {
        padding: 3px;
        border: none;
    }

    .input-row p * {
        flex: 1;
        margin: 10px;
    }

    .button-row {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
    }

    .button-row span {
        margin: 10px;
        padding: 4px;
    }

    .disabled {
        pointer-events: none;
        color: #3f3f3f !important;
    }

    @media screen and (max-width: 600px) {

        .input-row p {
            flex-wrap: wrap;
        }

        .input-row p * {
            flex: unset;
            width: 100%;
        }

        .editor > div:first-child, .editor > div:last-child {
            min-width: 50px;
            display: flex;
        }
    }

    .like-a {
        color: #ffd152;
    }

    .like-a:hover {
        color: #ffad2d !important;
    }

    @media screen and (max-width: 900px) {

        .room {
            margin: 3px;
        }

        #hotel label {
            width: 8vw;
        }

    }


</style>

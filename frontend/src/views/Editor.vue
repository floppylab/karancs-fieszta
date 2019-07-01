<template>
    <div>
        <the-header></the-header>
        <div v-if="this.frames.length > 0" class="editor">
            <div @click="previousFrame">
                <span v-show="current > 0">
                    <font-awesome-icon icon="angle-left" size="2x" :style="{ color: 'ivory' }"/>
                </span>
            </div>
            <div>
                <div class="input-row">
                    <p>
                        <label>cím:</label>
                        <input type="text" v-model="title"/>
                    </p>
                </div>
                <div class="input-row">
                    <p>
                        <label>hossz (ms):</label>
                        <input type="number" v-model="frames[current].time" step="500" min="500"/>
                    </p>
                    <p>
                        <label>animáció:</label>
                        <select v-model="frames[current].animation">
                            <option value="cut">nincs</option>
                            <!--<option value="random">random pixelek</option>-->
                            <!--<option value="wipe_up">beúszás lentről</option>-->
                            <!--<option value="wipe_down">beúszás fentről</option>-->
                            <!--<option value="wipe_left">beúszás jobbról</option>-->
                            <!--<option value="wipe_right">beúszás balról</option>-->
                        </select>
                    </p>
                </div>
                <div>{{ current + 1 }} / {{ frames.length }}</div>
                <div id="hotel" v-if="frames[current].data">
                    <div v-for="(floor, floorIndex) in frames[current].data" :key="floorIndex" class="floor">
                        <div v-for="(room, roomIndex) in floor" :key="roomIndex" class="room">
                            <input :id="'r' + floorIndex + roomIndex" type="checkbox"
                                   v-model="frames[current].data[floorIndex][roomIndex]"/>
                            <label :for="'r' + floorIndex + roomIndex"></label>
                        </div>
                    </div>
                </div>
            </div>
            <div @click="nextFrame">
                <span v-show="current < (frames.length -1)">
                    <font-awesome-icon icon="angle-right" size="2x":style="{ color: 'ivory' }"/>
                </span>
            </div>
        </div>

        <div class="button-row">
            <button @click="removeFrame">törlés</button>
            <button @click="clearFrame">alapállapot</button>
            <button @click="addFrame">hozzáadás</button>
            <button @click="saveAnimation">mentés</button>
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
        }]
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
      previousFrame() {
        this.current--
      },
      nextFrame() {
        this.current++
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
            this.$router.push('/')
          })
      },
      loadAnimation(id) {
        api.get(`/animations/${id}`)
          .then((response) => {
            console.log(response.data)
            this.id = response.data.id
            this.current = 0
            this.title = response.data.title
            this.frames = parseAnimation(response.data.code)
          })
      }
    },
    created() {
      if (this.$route.query.id) {
        this.loadAnimation(this.$route.query.id)
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
        justify-content: center;
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

    .button-row button {
        margin: 10px;
        min-width: 90px;
        border: none;
        padding: 4px;
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
    @media screen and (max-width: 900px) {

        .room {
            margin: 3px;
        }

        #hotel label {
            width: 8vw;
        }

    }


</style>

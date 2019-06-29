<template>
  <div>
    <div v-if="this.frames.length > 0" class="editor">
        <div>{{ current + 1 }} / {{ frames.length }}</div>
        <div id="hotel" v-if="frames[current].data">
          <div v-for="(floor, floorIndex) in frames[current].data" :key="floorIndex" class="floor">
            <div v-for="(room, roomIndex) in floor" :key="roomIndex" class="room">
              <input :id="'r' + floorIndex + roomIndex" type="checkbox" v-model="frames[current].data[floorIndex][roomIndex]"/>
              <label :for="'r' + floorIndex + roomIndex"></label>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'editor',
  data: () => {
    return {
      current: 0,
      frames: [{
        time: 1000,
        animation: 'cut',
        // data: new Array(7).fill(new Array(8).fill(false))
        data: [
          [ false, false, false, false, false, false, false, false ],
          [ false, false, false, false, false, false, false, false ],
          [ false, false, false, false, false, false, false, false ],
          [ false, false, false, false, false, false, false, false ],
          [ false, false, false, false, false, false, false, false ],
          [ false, false, false, false, false, false, false, false ],
          [ false, false, false, false, false, false, false, false ]
        ]
      }]
    }
  },
  methods: {
  }
}
</script>
<style>
  .floor {
    width: 90%;
    height: 14%;
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
    min-height: 60vh;
  }

  #hotel label {
    display: block;
    border: solid 1px #333;
    width: 5vw;
    max-width: 50px;
    height: 5vh;
    max-height: 25px;
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
  }

  .editor div:not(:first-child):not(:last-child) {
    flex: 3;
  }

  .input-row {
    display: flex;
    text-align: left;
    flex-wrap: wrap;
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

  .button-row button {
    margin: 10px;
    min-width: 100px;
    border: none;
    padding: 4px;
  }

</style>

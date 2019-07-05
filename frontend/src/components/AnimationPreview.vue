<template>
    <div>
        <p class="meta">
            <small>{{ animation.createdBy }}</small>
            <small>{{ formattedTotalTime }}</small>
        </p>
        <canvas :id="'preview' + animation.id" height="200" width="100"></canvas>
    </div>
</template>

<script>
import {parseAnimation} from '../functions'

export default {
  name: 'AnimationPreview',
  props: [ 'animation' ],
  data () {
    return {
      canvas: null,
      context: null,
      parsedAnimation: null,

      unit: 8,
      width: 2 * 8,
      height: 8
    }
  },
  computed: {
    formattedTotalTime () {
      if (!this.parsedAnimation) return
      return this.$moment(this.totalTime).format('mm:ss:SS')
    },
    totalTime () {
      let frameTimes = this.parsedAnimation.reduce((total, frame) => total + frame.time, 0)
      // console.log(frameTimes)
      let animationTimes = [].concat.apply([], this.parsedAnimation[0].data).filter(v => v).length  * 1500
      for(let i = 1; i <this.parsedAnimation.length; i++) {
        let numberOfDiffs = this.diff(this.parsedAnimation[i-1], this.parsedAnimation[i])
        animationTimes += numberOfDiffs * 1500
      }
      // console.log(animationTimes)
      return frameTimes + animationTimes;
    }
  },
  methods: {
    diff: function (from, to) {
      let count = 0
      for (let m = 0; m < from.data.length; m++) {
        for (let n = 0; n < from.data[m].length; n++) {
            if (from.data[m][n] != to.data[m][n]) count++
        }
      }
      return count
    },
    downloadAnimation: async function () {
      console.log('called')
      console.log(this.totalTime / 500)
      let encoder = new GIFEncoder();
      encoder.setRepeat(this.totalTime / 500);
      encoder.setDelay(500)
      encoder.start()

      let f = 0
      let currentTime = this.diff(this.previousFrame(0), this.parsedAnimation[0]) * 1500
      console.log(currentTime)
      for (let i = 0; i < this.totalTime / 500; i++) {
        this.context.clearRect(0, 0, this.canvas.width, this.canvas.height)
        let frame = this.parsedAnimation[f]
        this.draw(frame)
        encoder.addFrame(this.context)

        if (currentTime < i * 500) {
          if(f + 1 < this.parsedAnimation.length) f++
          let waitTime = frame.time + this.diff(this.previousFrame(f), frame) * 1500
          currentTime += waitTime
        }

        console.log(this.parsedAnimation.length)
        console.log(f)
      }
      encoder.finish()
      const toKebabCase = str =>
        str &&
        str
          .normalize("NFD").replace(/[\u0300-\u036f]/g, "")
          .match(/[A-Z]{2,}(?=[A-Z][a-z]+[0-9]*|\b)|[A-Z]?[a-z]+[0-9]*|[A-Z]|[0-9]+/g)
          .map(x => x.toLowerCase())
          .join('-');
      console.log(toKebabCase(this.animation.title.toLowerCase()) + '.gif')
      encoder.download(toKebabCase(this.animation.title.toLowerCase()) + '.gif');
    },
    draw: function (frame) {
      for (let m = 0; m < frame.data.length; m++) {
        for (let n = 0; n < frame.data[m].length; n++) {
          let x = (3 * n + 1) * this.unit
          let y = (2 * m + 1) * this.unit
          if (frame.data[m][n]) {
            this.context.fillRect(x, y, this.width, this.height)
          } else {
            this.context.strokeRect(x, y, this.width, this.height)
          }
        }
      }
    },
    previousFrame: function (i) {
      if (i == 0) return {data: new Array(7).fill(new Array(8).fill(false))}
      return this.parsedAnimation[(i - 1) % this.parsedAnimation.length]
    }
  },
  async mounted() {
    this.canvas = document.getElementById('preview' + this.animation.id)
    this.canvas.width = 200
    this.canvas.height = 125

    this.context = this.canvas.getContext('2d')
    this.context.fillStyle = '#fffff0'
    this.context.strokeStyle = '#373734'
    this.context.lineWidth = 1

    this.parsedAnimation = parseAnimation(this.animation.code)

    let i = 0
    while (true) {
      let frame = this.parsedAnimation[i]
      this.draw(frame)
      let waitTime = frame.time + this.diff(this.previousFrame(i), frame) * 1500
      await new Promise(resolve => setTimeout(resolve, waitTime));
      i = (i + 1) % this.parsedAnimation.length
      this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);
    }
  }
}
</script>

<style scoped>

    .meta {
        width: 185px;
        margin: 0 auto;
        display: flex;
        justify-content: space-between;
    }

    p {
        margin-bottom: 0;
    }

</style>
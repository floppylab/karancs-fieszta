<template>
    <canvas :id="'preview' + animation.id" height="200" width="100"></canvas>
</template>

<script>
import {parseAnimation} from '../functions'

export default {
  name: 'AnimationPreview',
  props: [ 'animation' ],
  async mounted() {
    let canvas = document.getElementById('preview' + this.animation.id)
    canvas.width = 200
    canvas.height = 125

    let context = canvas.getContext('2d')
    context.fillStyle = '#fffff0'
    context.strokeStyle = '#373734'
    context.lineWidth = 1

    let animation = parseAnimation(this.animation.code)
    console.log(animation)

    let unit = 8
    let width = 2 * unit
    let height = unit

    let i = 0
    while (true) {
      let frame = animation[i]
      for (let m = 0; m < frame.data.length; m++) {
        for (let n = 0; n < frame.data[m].length; n++) {
          let x = (3 * n + 1) * unit
          let y = (2 * m + 1) * unit
          if (frame.data[m][n]) {
            context.fillRect(x, y, width, height)
          } else {
            context.strokeRect(x, y, width, height)
          }
        }
      }
      await new Promise(resolve => setTimeout(resolve, frame.time));
      i = (i + 1) % animation.length
      context.clearRect(0, 0, canvas.width, canvas.height);
    }
  }
}
</script>

<style scoped>

</style>
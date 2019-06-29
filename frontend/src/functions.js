export function stringifyAnimation(animation) {
  let str = ''
  animation.forEach(frame => {
    str += `${frame.time},${frame.animation}\n`
    for (let i = 0; i < frame.data.length; i++) {
      for (let j = 0; j < frame.data[i].length; j++) {
        str += frame.data[i][j] ? '#' : '.'
      }
      str += '\n'
    }
    str += '\n'
  })
  return str
}

export function parseAnimation(str) {
  let animation = []
  let frames = str.split('\n\n')
  frames.forEach(frameStr => {
    let frame = {
      time: null,
      animation: null,
      data: []
    }
    let lines = frameStr.split('\n')
    let header = lines.shift().split(',')
    frame.time = parseInt(header[0])
    frame.animation = header[1]
    lines.forEach(line => {
      line = line.split('').map(c => (c === '#' ? true : false))
      frame.data.push(line)
    })
    animation.push(frame)
  })
  return animation
}
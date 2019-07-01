<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>

<script>

  export default {
    name: 'app'
  }
</script>

<style lang="scss">
    #app {
        font-family: 'Patrick Hand', sans-serif;
        font-weight: 300;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        color: ivory;
        min-height: 100vh;
        justify-content: center;
        flex-direction: column;
        align-items: center;
        display: flex;
    }

    a, a:link, a:active, a:visited, a:hover {
        color: #ffd152;
        text-decoration: none;
    }

    a:hover {
        color: #ffad2d !important;
        text-decoration: none !important;
    }

    h2 {
        color: #ffad2d !important;
        padding-top: 20px;
    }

    body {
        background-color: #222 !important;
        margin: 0;
    }

    section {
        padding: 20px;
        max-width: 1000px;
    }

    .dg-main-content {
        border-radius: 0 !important;
        font-family: 'Patrick Hand';
    }

    .dg-content-body {
        border-bottom: none !important;
    }

    .dg-btn--cancel {
        border: none !important;
        background-color: #ffd152 !important;
        padding: 3px 20px !important;
        color: #222 !important;
        border-radius: 0 !important;
        box-sizing: border-box;
    }

    .dg-btn--ok {
        box-sizing: border-box;
        border: 3px #ffd152 solid !important;
        padding: 3px 20px !important;
        color: #222 !important;
        border-radius: 0 !important;
    }

    @media screen and (max-width: 600px) {
        footer {
            padding: 20px 0;
        }
    }

</style>

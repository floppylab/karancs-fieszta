<template>
  <div>
    <the-header></the-header>
    <main>
      <section id="jumbotron"></section>
      <section>
        <h2>mi is ez?</h2>
        <p>A Kísérletváros - DemoLab csoport egyestés fesztiválja, mely során szeretnénk minden érdeklődővel megosztani több éves kutatásunk és alkotásunk eredményeit.</p>
        <p>A salgótarjáni csoport fókuszába a város szívében évek óta üresen álló, a hatvanas, hetvenes években fénykorát élő Karancs Hotel került.
          A város lakóinak emlékeiből, archív újságcikkekből, rokonok emlékeiből rekonstruáltuk Salgótarján egyik szimbólumának történetét.</p>
        <p>További információ a fenti menüből elérhető Facebook esemény leírásában található.</p>
        <p>Ezen az oldalon az esti meglepetésprogramhoz lehet elkészíteni az animációkat, egy gyors regisztráció után.</p>
      </section>
      <section>
        <h2>már elkészített animációk</h2>
        <p>az itt látható animációk fognak megjelenni a Karancs ablakaiban</p>
        <p>bejelentkezett felhasználók tudnak szavazni a szívre kattintással vagy az animáción dupla kattintással</p>
        <animation-lister></animation-lister>
      </section>
    </main>
    <the-footer></the-footer>
  </div>
</template>

<script>

import AnimationLister from '../components/AnimationLister'
import TheHeader from '../components/TheHeader'
import TheFooter from '../components/TheFooter'

export default {
  name: 'home',
  components: {
    TheFooter,
    TheHeader,
    AnimationLister
  }
}
</script>

<style>

  #jumbotron {
    background-image: url("../assets/bg.jpg");
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
    height: 40vh;
  }

</style>

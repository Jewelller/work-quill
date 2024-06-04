<template>
    <div>Home Page</div>
    <NuxtLink to="/login">Login Page</NuxtLink>
</template>

<script setup>

class A {
    constructor() {
        this.a = 1
        this.b = 2
        this.c = 3
    }

    hello() {
        console.log(this.a, this.b, this.c);
        const func = () => {
            console.log(this.a, this.b, this.c);
        }
        func()
    }
}

let a = new A()
a.hello()
</script>

<style></style>

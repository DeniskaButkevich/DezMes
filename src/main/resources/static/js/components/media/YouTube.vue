<template>
    <iframe
            width="560"
            height="315"
            :src="targetSrc"
            frameborder="0"
            allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
            allowfullscreen
    ></iframe>
</template>

<script>
    export default {
        name: 'YouTube',
        props: ['src'],
        computed: {
            targetSrc() {
                let parts = youtube_parser(this.src)
                return `https://www.youtube.com/embed/${parts}`
            }
        }
    }

    function youtube_parser(url){
        let regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#&?]*).*/;
        let match = url.match(regExp);
        return (match&&match[7].length==11)? match[7] : false;
    }
</script>

<style scoped>
</style>
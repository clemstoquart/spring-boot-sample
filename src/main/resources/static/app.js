'use strict';

new Vue({
    el: '#app',
    data: {
        message: 'Hello Vue.js!',
        movies: {}
    },
    created: function () {
        this.getMovies()
    },
    methods: {
        getMovies: function () {
            const xhr = new XMLHttpRequest();
            let self = this;
            xhr.open('GET', 'http://localhost:8080/movies');
            xhr.onload = function () {
                self.movies = JSON.parse(xhr.responseText);
            };
            xhr.send();
        }
    }
});

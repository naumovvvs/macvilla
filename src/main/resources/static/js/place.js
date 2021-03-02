


let path = window.location.pathname;
let pom = "";

for(var i=path.length-1; i>=0; i--){
    if(path[i]==="/")
    break;

    pom = pom + path[i];
}

var split = pom.split("");
var reverse = split.reverse();
var id = reverse.join("");

const apiURL = "http://localhost:8080/api/place/" + id;


var vm = new Vue({
    el: "#app",
    data(){
        return{
            place: {},
            errors: [],
            reviews: []
        }
    },
    created(){
        fetch(apiURL)
        .then(response => {
            return response.json()
        })
        .then(place => {
            this.place = place
        });

        fetch(apiURL+"/reviews")
        .then(response => {
            return response.json()
        })
        .then(reviews => {
            this.reviews = reviews;
        });
    },
    methods: {
        // postReview: function (){
        //     // import axios from 'axios';
        //
        //     let radio = document.querySelector('input[name="ratingStars"]:checked');
        //     let reviewContent = document.getElementById("reviewContent");
        //
        //     if(radio === null || radio.value === "" || reviewContent === null || reviewContent.value === ""){
        //         if(radio!==null)
        //             radio.checked = false;
        //
        //         if(reviewContent!==null)
        //             reviewContent.value = "";
        //
        //         return;
        //     }
        //
        //
        //     // Simple POST request with a JSON body using fetch
        //     const requestOptions = {
        //         method: "POST",
        //         headers: { "Content-Type": "application/json" },
        //         body: JSON.stringify({ title: "Vue POST Request Example" })
        //     };
        //     fetch(apiURL + "/reviews/new", requestOptions)
        //         .then(response => response.json())
        //         .then(data => (this.postId = data.id));
        //
        //     console.log(radio.value);
        //     console.log(reviewContent.value);
        //
        //     radio.checked = false;
        //     reviewContent.value = "";
        // }
    }
});

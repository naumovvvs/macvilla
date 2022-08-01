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
const reviewsApiURL = "http://localhost:8080/api/reviews/" + id;


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

        fetch(reviewsApiURL)
        .then(response => {
            return response.json()
        })
        .then(reviews => {
            this.reviews = reviews;
        });
    },
    methods: {
        fixDate: (date) => {
            let parts = date.split("T");
            return parts[0];
        }
    }
});

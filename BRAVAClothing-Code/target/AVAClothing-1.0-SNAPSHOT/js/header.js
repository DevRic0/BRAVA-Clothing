function nav() {
    var x = document.getElementById("myTopnav");
    if (x.className === "topnav") {
        x.className += " responsive";
    } else {
        x.className = "topnav";
    }
}

function enterKey(){
    $("#search").keypress(function(event) {
        if (event.keyCode === 13) {
            search();
        }
    });
}

function search(){
    var x=document.getElementById("search").value;
    var xmlhttp= new XMLHttpRequest();
    xmlhttp.onreadystatechange= function() {
        if (this.readyState==4 && this.status==200){
            searchDB(this);
        }
    };
    /**
     * La versione a riga 34 funziona per l'applicazione all'interno di IntelliJ
     * La versione a riga 37 funziona al di fuori di IntelliJ
     */
    var ctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
    var exploded = '/AVAClothing_war_exploded';
    var snapshot = '/AVAClothing-1.0-SNAPSHOT';
    if(ctx == exploded){
        xmlhttp.open("GET","/AVAClothing_war_exploded/JSON?data="+x,true);
    }
    else if (ctx == snapshot){
        xmlhttp.open("GET","/AVAClothing-1.0-SNAPSHOT/JSON?data="+x,true);
    }
    xmlhttp.send();
}

function searchDB(xmlhttp){
    var result=JSON.parse(xmlhttp.responseText);
    let text;
    text="<div class='dropdown-search'>";
    for (let i in result){
        var x=JSON.stringify(result[i].nome);
        var y=JSON.stringify(result[i].idProdotto);
        idp = eval(y);
        /**
         * La versione a riga 58 funziona per l'applicazione all'interno di IntelliJ
         * La versione a riga 61 funziona al di fuori di IntelliJ
         */
        var ctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
        var exploded = '/AVAClothing_war_exploded';
        var snapshot = '/AVAClothing-1.0-SNAPSHOT';
        if(ctx == exploded){
            text+="<a href='/AVAClothing_war_exploded/cliente/product?id="+idp+"'>";
        }
        else if (ctx == snapshot){
            text+="<a href='/AVAClothing-1.0-SNAPSHOT/cliente/product?id="+idp+"'>";
        }
        text+=x+"</a>";
    }
    text+="</div>";
    document.getElementById("demo").hidden=false;
    document.getElementById("demo").innerHTML= text;//id elemento
}

function hiddenSearch(){
    $("#demo").hide();
}

function showSearch(){
    $("#demo").show();
}
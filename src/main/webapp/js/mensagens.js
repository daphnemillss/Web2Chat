/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

$(document).ready(function () {
    var user1 = getUrlParameter('user1');
    var user2 = getUrlParameter('user2');
    
    setInterval(function () {
        getMessages(user1, user2);
    }, 5000);
    
});

function getMessages(user1, user2){
    $.ajax({
        url:'Mensagem',
        type: 'GET',
        data:{user1, user2},
        statusCode:{
            200: function(messagesw){
                
                alert('sucesso\n\n');
            },
            400: function(){
                alert('erro');
            }
        }
    });
}
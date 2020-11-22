var app = angular.module('bracketApp', []);

app.controller("bracketControl", function($scope){
   
    $scope.targetPlayer = "";
    $scope.bracketToDisplay = "";
    $scope.showingBracket = false;

    $scope.brackets = [{
        "bracketName": "Senior Men 145",
        "rounds": [{
            "matches": [{
                "player1": "Bob Rea",
                "player2": "Nick Lowry",
                "matchNumber": 1
            }, {
                "player1": "Greg Ables",
                "player2": "Brian Head",
                "matchNumber": 2
            }, {
                "player1": "Howard Popkin",
                "player2": "Kyle Sloan",
                "matchNumber": 3
            }]
        }, {
            "matches": [{
                "player1": "NULL",
                "player2": "NULL",
                "matchNumber": 6
            }, {
                "player1": "NULL",
                "player2": "NULL",
                "matchNumber": 7
            }]
        }, {
            "matches": [{
                "player1": "NULL",
                "player2": "NULL",
                "matchNumber": 9
            }]
        }]
    }, {
        "bracketName": "Senior Women 125",
        "rounds": [{
            "matches": [{
                "player1": "Ronda R",
                "player2": "Cat Z",
                "matchNumber": 4
            }, {
                "player1": "M T",
                "player2": "Amanda N",
                "matchNumber": 5
            }]
        }, {
            "matches": [{
                "player1": "NULL",
                "player2": "NULL",
                "matchNumber": 8
            }]
        }]
    }]

    $scope.findBracket = function(){
    //function: findBracket
    //Loop through the first round of each bracket
    //searching for targetPlayer
        for (let b=0; b<$scope.brackets.length; b++ ){

            for (let m=0; m<$scope.brackets[b].rounds[0].matches.length; m++){
                if($scope.targetPlayer.toUppercase===$scope.brackets[b].rounds[0].matches[m].player1.toUppercase ||
                   $scope.targetPlayer.toUppercase===$scope.brackets[b].rounds[0].matches[m].player2.toUppercase){
                    $scope.bracketToDisplay = $scope.brackets[b];
                    $scope.showingBracket = true;
                    return;
                }
            }
        }
    };

});
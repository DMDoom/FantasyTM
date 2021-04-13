# fantasytm
```javascript
// Manage player availability to only allow possible team compositions
// Allow only one checkbox in column to be active, ensure team is made of unique players, and manage balance bar
function onlyOne(checkbox, tableName, id) {
    // Player lists
    var checkboxes = document.getElementById(tableName).querySelectorAll(".buyPlayer"); 
    var players = document.querySelectorAll(".buyPlayer");
    var columns = document.getElementsByName("column");

    // Progress bar variables
    var currBalancePoints = document.querySelector(".progress-bar-striped > div").textContent.replace(' points', '');
    var currBalancePercent = document.querySelector(".progress-bar-striped > div").style.width.replace(/%/g, '');
    var totalBalancePoints = 650; // must be the same as specified in HTML initial value
    var playerValuePoints = checkbox.parentNode.getElementsByTagName("span")[0].innerHTML;
    var playerValuePercent = Math.round(playerValuePoints / totalBalancePoints * 100);
    var toUpdatePercent;
    var toUpdatePoints;
    
    // On checkbox checked
    if (checkbox.checked) {
      
      // Subtract points from balance
      toUpdatePercent = currBalancePercent - playerValuePercent;
      toUpdatePoints = currBalancePoints - playerValuePoints;
      change(toUpdatePercent, toUpdatePoints);
      
      // Disable all players in a column
      checkboxes.forEach((item) => {
        if (item !== checkbox) {
          item.checked = false
          item.disabled = true
        }
      })
      
      // Disable the same player in all columns
      players.forEach((player) => { 
        if (player.id == id && player !== checkbox) {
        player.disabled = true;
        }
      })
      
      // Disable all players too expensive
      currBalancePoints = toUpdatePoints;
      players.forEach((player) => {
        var playerValue = player.parentNode.getElementsByTagName("span")[0].innerHTML;
        if (playerValue > currBalancePoints && player !== checkbox && !player.checked) {
          player.disabled = true;
        }
      })
      
    // On checkbox unchecked
    } else {
      
      // Restore points to balance
      toUpdatePercent = +currBalancePercent + +playerValuePercent;
      toUpdatePoints = +currBalancePoints + +playerValuePoints;
      change(toUpdatePercent, toUpdatePoints);
      var selectedPlayers = gatherSelected();
      
      // Enable all possible players back on
      var currBalancePoints = toUpdatePoints;
      players.forEach((player) => {
        var playerValue = player.parentNode.getElementsByTagName("span")[0].innerHTML;
        // If there is absolutely no reason this checkbox should be disabled, enable it
        if (!selectedPlayers.includes(player.id) 
            && !isSomethingChecked(player.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode) 
            && playerValue < currBalancePoints) {
           player.disabled = false;
        }        
      })
    }
}

// Returns a list of IDs of all selected players
function gatherSelected() {
  var players = document.querySelectorAll(".buyPlayer");
  var takenPlayers = [];
  
  players.forEach((player) => {
    if (player.checked) {
      takenPlayers.push(player.id);
    }
  })
  
  return takenPlayers;
}

// Checks if any player is selected in the passed column
function isSomethingChecked(column) {
  var playersInColumn = column.querySelectorAll(".buyPlayer");
  
  var counter = 0;
  playersInColumn.forEach((player) => { 
     if (player.checked) {
        counter++;
     }
  })
  
  if (counter == 0) {
    return false;
  } else {
    return true;
  }
}

// Change the balance bar to represent specified values
function change(percentValue, pointsValue) {
				document.querySelector(".progress-bar-striped > div").textContent = pointsValue + " points";
				document.querySelector(".progress-bar-striped > div").style.width = percentValue + "%";
}
```

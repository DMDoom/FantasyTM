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
**CSS**
```css
:root {
  --white: #efefef;
}

body {
  background-color: #80FF80;
  background-image: url(https://www.gamegrin.com/assets/game/trackmania-2020/screenshots/_resampled/SetWidth1920-trackmania-2020-screenshots-11.jpg);
  background-repeat: no-repeat;
  background-attachment: fixed;
  margin:0;
}

.image-wrap{
    background: rgba(248, 248, 255, 0.3);
    overflow: hidden; /* needs scroll for local version */
    height: 100%;
    z-index: 2;
}

/* Style the header with a grey background and some padding */
.header {
  overflow: hidden;
  background-color: #fafafa;
  padding: 20px 100px;
  box-shadow: 2px 2px 2px #b3b3b3;
}

/* Style the header links */
.header a {
  margin: 8px;
  margin-left: 20px;
  margin-right: 20px;
  float: left;
  color: #232323;
  text-align: center;
  padding: 12px;
  text-decoration: none;
  letter-spacing: 1px;
  border-radius: 4px;
  font-family: 'Rubik';
  font-size: 18px;
  font-weight: bold;
}

/* Style the logo link (notice that we set the same value of line-height and font-size to prevent the header to increase when the font gets bigger */
.header a.logo {
  font-weight: bold;
  font-family: 'Rubik';
  font-size: 25px;
  color: #232323;
}

/* Change the background color on mouse-over */
.header a:hover {
  background-color: #ddd;
  color: black;
}

/* Float the link section to the right */
.header-right {
  float: right;
}

.button {
  position: relative;
  margin-left: 20px;
  background-color: #6edc45;
  border: none;
  font-size: 18px;
  font-weight: bold;
  font-family: 'Rubik';
  color: #FFFFFF;
  letter-spacing: 1px;
  padding: 20px;
  width: 250x;
  text-align: center;
  -webkit-transition-duration: 0.4s; /* Safari */
  transition-duration: 0.4s;
  text-decoration: none;
  overflow: hidden;
  cursor: pointer;
  border-radius: 6px;
  box-shadow: 3px 3px 3px #b3b3b3;
}

.button:after {
  content: "";
  background: #90EE90;
  display: block;
  position: absolute;
  padding-top: 300%;
  padding-left: 350%;
  margin-left: -20px!important;
  margin-top: -120%;
  opacity: 0;
  transition: all 0.8s
}

.button:active:after {
  padding: 0;
  margin: 0;
  opacity: 1;
  transition: 0s
}

.button:hover {
  background-color: #4CAF50; /* Green */
  color: white;
}

.button:disabled {
  background-color: #909090;
  color:#fff; 
}

/* Add media queries for responsiveness - when the screen is 500px wide or less, stack the links on top of each other */
@media screen and (max-width: 500px) {
  .header a {
    float: none;
    display: block;
    text-align: left;
  }
  .header-right {
    float: none;
  }
}

p {
  float: left;
  margin-left: 20px;
  text-align: left;
  font-family: 'Rubik';
  font-size: 17px;
  color: #232323;
  font-weight: bold;
  /*padding-left: 47%;*/
}

#testtt p {
  padding-top: 30px important!;
  font-size: 30px;
  font-weight: bold;
  font-family: 'Rubik';
  color: var(--white);
  padding-left: 47%;
}

h1 {
  text-align: center;
  font-family: 'Rubik';
  font-size: 17px;
  color: #232323;
  font-weight: bold;
}

h2 {
  text-align: center;
  font-family: 'Rubik';
  font-size: 12px;
  color: #666666;
}

.grid-container {
  margin-top: 20px;
  margin-left: 70px;
  margin-right: 70px;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  grid-template-rows: 1fr;
  gap: 0px 0px;
  grid-template-areas:
    "one two three four five";
  grid-column-gap: 85px;
}

.one {
  grid-area: one;
  background-color: var(--white);
  border-radius: 30px;
  max-width: 300px;
  box-shadow: 2px 2px 2px grey;
  border: 1px solid grey;
}

.two {
  grid-area: two; 
  background-color: var(--white);
  border-radius: 30px; /* was 12px */
  max-width: 300px;
  box-shadow: 2px 2px 2px grey;
  border: 1px solid grey;
}

.three {
  grid-area: three;
  background-color: var(--white);
  border-radius: 30px;
  max-width: 300px;
  box-shadow: 2px 2px 2px grey;
  border: 1px solid grey;
}

.four {
  grid-area: four;
  background-color: var(--white);
  border-radius: 30px;
  max-width: 300px;
  box-shadow: 2px 2px 2px grey;
  border: 1px solid grey;
}

.five {
  grid-area: five;
  background-color: var(--white);
  border-radius: 30px;
  max-width: 300px;
  box-shadow: 2px 2px 2px grey;
  border: 1px solid grey;
}

td, th {
  margin: 5px;
}

tr:nth-child(even) {
  background-color: #dddddd;
  border-radius: 30px;
}

/*
Something like this instead of HTML hardcoded
For last child class name
For last child class price
tr:last-child {
  border-radius: 0px 0px 0px 30px;
}
*/

/*
.testt {
  border-radius: 30px;
  background-color: blue;
}
*/

img {
  position: relative;
  padding: 20px;
  margin-left: 30px;
}

/* Progress bar with CSS and JS */
.progress {
  margin-top: 50px;
  margin-bottom: 50px;
  margin-left: 70px;
  margin-right: 70px;
  background-color: var(--white);
  border-radius: 20px;
  height: 50px;
  width: auto;
  box-shadow: 2px 2px 2px grey;
}

/* Button-like checkbox attempts */
#ck-button {
    margin:4px;
    margin-right:20px;
    background-color: #6edc45;
    font-size: 17px;
    font-weight: bold;
    font-family: 'Rubik';
    color: #FFFFFF;
    border-radius:6px;
    border:1px solid #D0D0D0;
    overflow:auto;
    float:right;
    /* testing */
    transition: .2s;
}

#ck-button:hover {
    background-color: #4CAF50 !important;
}

#ck-button label {
    float:left;
    width:6.0em;
}

#ck-button label span {
    text-align:center; 
    padding: 20px;
    display:block;
    cursor: pointer;
}

#ck-button label input {
    position:fixed; /* was absolute, but that causes site to jump */
    top:-20px;
}

#ck-button input:checked + span {
    background-color:#4A7023;
    color:#fff;
}

#ck-button input:disabled + span {
    background-color: #909090;
    color:#fff; 
}

/* Prograss bar */
.progress-bar-striped {
		overflow: hidden;
		height: 80px;
    margin-top: 60px;
		margin-bottom: 60px;
    margin-left: 75px; /* placeholder margins */
    margin-right: 75px; /* placeholder margins */
		background-color: #dddddd;
		border-radius: 20px;
		-webkit-box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);
		-moz-box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);
    box-shadow: 2px 2px 2px grey;
    border: 1px solid grey;
}

.progress-bar-striped > div {
		background-image: linear-gradient(45deg, rgba(135, 222, 74, 1) 25%, transparent 25%, transparent 50%, rgba(135, 222, 74, 1) 50%, rgba(135, 222, 74, 1) 75%, transparent 75%, transparent);
		background-size: 40px 40px;
		width: 0%;
		height: 100%;
		font-size: 12px;
		line-height: 20px;
		text-align: center;
		-webkit-box-shadow: inset 0 -1px 0 rgba(0, 0, 0, 0.15);
		-moz-box-shadow: inset 0 -1px 0 rgba(0, 0, 0, 0.15);
		box-shadow: inset 0 -1px 0 rgba(0, 0, 0, 0.15);
		-webkit-transition: width 3s ease;
		-moz-transition: width 3s ease;
		-o-transition: width 3s ease;
		transition: width 3s ease;
		animation: progress-bar-stripes 2s linear infinite;
		background-color: #6edc45;
    font-size: 30px;
    font-weight: bold;
    font-family: 'Rubik';
    color: var(--white);
    padding-top: 30px;
}

.progress-bar-striped p{
				margin: 0;
}
			
@keyframes progress-bar-stripes {
				0% { background-position: 40px 0;}
				100% {background-position: 0 0;}
}

input[type=text] {
  height: 65px;
  float: center;
  box-sizing: border-box;
  font-size: 35px;
  font-weight: bold;
  font-family: 'Rubik';
  color: black;
  display: block;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 25px;
  width: 40%;
  text-align: center;
  width: 300px;
}

.buttonSubmit {
  position: relative;
  background-color: #6edc45;
  border: none;
  font-size: 35px;
  font-weight: bold;
  font-family: 'Rubik';
  color: #FFFFFF;
  letter-spacing: 1px;
  padding: 30px;
  width: 250x;
  text-align: center;
  -webkit-transition-duration: 0.4s; /* Safari */
  transition-duration: 0.4s;
  text-decoration: none;
  overflow: hidden;
  cursor: pointer;
  border-radius: 6px;
  box-shadow: 3px 3px 3px #b3b3b3;
  display: block;
  margin-left: auto;
  margin-right: auto;
}

.buttonSubmit:after {
  content: "";
  background: #90EE90;
  display: block;
  position: absolute;
  padding-top: 300%;
  padding-left: 350%;
  margin-left: -20px!important;
  margin-top: -120%;
  opacity: 0;
  transition: all 0.8s
}

.buttonSubmit:active:after {
  padding: 0;
  margin: 0;
  opacity: 1;
  transition: 0s
}

.buttonSubmit:hover {
  background-color: #4CAF50; /* Green */
  color: white;
}

.submitBox {
  background-color: var(--white);
  padding-top: 30px;
  padding-bottom: 30px;
  margin-top: 25px;
  margin-bottom: 25px;
  display: block;
  margin-left: 35%;
  margin-right: 35%;
  border-radius: 30px;
  border: 1px solid grey;
  box-shadow: 2px 2px 2px grey;
}
```

**HTML**
```html
<head>
  <link href='https://fonts.googleapis.com/css?family=Rubik' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="stylesCreateTeam.css">
</head>

<body>
  <div class="image-wrap">

  <div class="header">
    <a href="#default" class="logo">FANTASY TM</a>
    <div class="header-right">
      <a href="#about">Rankings</a>
      <a href="#contact">Register</a>
      <a href="#about">Login</a>
      <button class="button" disabled>CREATE YOUR TEAM</button>
    </div>
  </div>
    
<div class="progress-bar-striped" id="testtt">
			<div style="width: 100%;"><b><p>650 points</p></b></div>
</div>
  
  
<div class="grid-container">
    
    <div class="one">
      <h1>CAPTAIN</h1>
      <h2>2x points</h2>
    </div>
    
    <div class="two">
      <h1>PLAYER</h1>
      <h2>1x points</h2>
    </div>
    
    <div class="three"></img>
    <h1>PLAYER</h1>
    <h2>1x points</h2>
    </div>
    
    <div class="four">
      <h1>PLAYER</h1>
      <h2>1x points</h2>
    </div>
    
    <div class="five">
      <h1>UNDERDOG</h1>
      <h2>2x points in playoffs</h2>
    </div>
  </div>  

  
<div class="grid-container" id="grid">

  <div class="one" id="captainsTable" name="column">
    <table style="width:100%" cellspacing="0">
      <tr>
        <td class="name"><p>CarlJr.</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 1)" id="1">
                 <span>369</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Pac</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 2)" id="2">
                 <span>275</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Aurel</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 3)" id="3">
                 <span>128</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Gwen</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 4)" id="4">
                 <span>126</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Affi</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 5)" id="5">
                  <span>116</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Mudda</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 6)" id="6">
                  <span>102</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Bren</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 7)" id="7">
                  <span>89</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Papou</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 8)" id="8">
                  <span>88</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Spam</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 9)" id="9">
                  <span>85</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Massa</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 10)" id="10">
                  <span>82</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Kappa</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 11)" id="11">
                  <span>68</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>tween</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 12)" id="12">
                  <span>63</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>riolu</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 13)" id="13">
                  <span>53</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Scrapie</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 14)" id="14">
                  <span>43</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>evoN</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 15)" id="15">
                  <span>28</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name" style="border-radius: 0px 0px 0px 30px;"><p>link</p></td>
        <td class="price" style="border-radius: 0px 0px 30px 0px;">
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'captainsTable', 16)" id="16">
                  <span>23</span>
              </label>
          </div>
        </td>
      </tr>
    </table>
  </div>

  <div class="two" id="regularsTable1" name="column">
    <table style="width:100%" cellspacing="0">
      <tr>
        <td class="name"><p>CarlJr.</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 1)" id="1">
                 <span>369</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Pac</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 2)" id="2">
                 <span>275</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Aurel</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 3)" id="3">
                  <span>128</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Gwen</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 4)" id="4">
                 <span>126</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Affi</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 5)" id="5">
                  <span>116</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Mudda</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 6)" id="6">
                  <span>102</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Bren</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 7)" id="7">
                  <span>89</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Papou</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 8)" id="8">
                  <span>88</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Spam</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 9)" id="9">
                  <span>85</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Massa</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 10)" id="10">
                  <span>82</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Kappa</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 11)" id="11">
                  <span>68</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>tween</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 12)" id="12">
                  <span>63</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>riolu</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 13)" id="13">
                  <span>53</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Scrapie</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 14)" id="14">
                  <span>43</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>evoN</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 15)" id="15">
                  <span>28</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name" style="border-radius: 0px 0px 0px 30px;"><p>link</p></td>
        <td class="price" style="border-radius: 0px 0px 30px 0px;">
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable1', 16)" id="16">
                  <span>23</span>
              </label>
          </div>
        </td>
      </tr>
    </table>
  </div>
    
  <div class="three" id="regularsTable2" name="column">
    <table style="width:100%" cellspacing="0">
      <tr>
        <td class="name"><p>CarlJr.</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 1)" id="1">
                 <span>369</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Pac</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 2)" id="2">
                 <span>275</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Aurel</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 3)" id="3">
                  <span>128</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Gwen</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 4)" id="4">
                 <span>126</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Affi</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 5)" id="5">
                  <span>116</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Mudda</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 6)" id="6">
                  <span>102</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Bren</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 7)" id="7">
                  <span>89</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Papou</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 8)" id="8">
                  <span>88</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Spam</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 9)" id="9">
                  <span>85</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Massa</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 10)" id="10">
                  <span>82</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Kappa</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 11)" id="11">
                  <span>68</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>tween</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 12)" id="12">
                  <span>63</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>riolu</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 13)" id="13">
                  <span>53</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Scrapie</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 14)" id="14">
                  <span>43</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>evoN</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 15)" id="15">
                  <span>28</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name" style="border-radius: 0px 0px 0px 30px;"><p>link</p></td>
        <td class="price" style="border-radius: 0px 0px 30px 0px;">
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable2', 16)" id="16">
                  <span>23</span>
              </label>
          </div>
        </td>
      </tr>
    </table>
  </div>
    
  <div class="four" id="regularsTable3" name="column">
    <table style="width:100%" cellspacing="0">
      <tr>
        <td class="name"><p>CarlJr.</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 1)" id="1">
                 <span>369</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Pac</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 2)" id="2">
                 <span>275</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Aurel</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 3)" id="3">
                  <span>128</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Gwen</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 4)" id="4">
                 <span>126</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Affi</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 5)" id="5">
                  <span>116</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Mudda</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 6)" id="6">
                  <span>102</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Bren</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 7)" id="7">
                  <span>89</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Papou</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 8)" id="8">
                  <span>88</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Spam</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 9)" id="9">
                  <span>85</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Massa</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 10)" id="10">
                  <span>82</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Kappa</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 11)" id="11">
                  <span>68</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>tween</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 12)" id="12">
                  <span>63</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>riolu</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 13)" id="13">
                  <span>53</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Scrapie</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 14)" id="14">
                  <span>43</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>evoN</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 15)" id="15">
                  <span>28</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name" style="border-radius: 0px 0px 0px 30px;"><p>link</p></td>
        <td class="price" style="border-radius: 0px 0px 30px 0px;">
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'regularsTable3', 16)" id="16">
                  <span>23</span>
              </label>
          </div>
        </td>
      </tr>
    </table>
  </div>
    
  <div class="five" id="underdogsTable" name="column">
    <table style="width:100%" cellspacing="0">
      <tr>
        <td class="name"><p>CarlJr.</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 1)" id="1">
                 <span>369</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Pac</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 2)" id="2">
                 <span>275</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Aurel</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 3)" id="3">
                  <span>128</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Gwen</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 4)" id="4">
                 <span>126</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Affi</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 5)" id="5">
                  <span>116</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Mudda</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 6)" id="6">
                  <span>102</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Bren</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 7)" id="7">
                  <span>89</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Papou</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 8)" id="8">
                  <span>88</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Spam</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 9)" id="9">
                  <span>85</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Massa</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 10)" id="10">
                  <span>82</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Kappa</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 11)" id="11">
                  <span>68</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>tween</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 12)" id="12">
                  <span>63</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>riolu</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 13)" id="13">
                  <span>53</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>Scrapie</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 14)" id="14">
                  <span>43</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name"><p>evoN</p></td>
        <td>
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 15)" id="15">
                  <span>28</span>
              </label>
          </div>
        </td>
      </tr>
      <tr>
        <td class="name" style="border-radius: 0px 0px 0px 30px;"><p>link</p></td>
        <td class="price" style="border-radius: 0px 0px 30px 0px;">
          <div id="ck-button">    
              <label>
                 <input class="buyPlayer" type="checkbox" name="players" value="1" onclick="onlyOne(this, 'underdogsTable', 16)" id="16">
                  <span>23</span>
              </label>
          </div>
        </td>
      </tr>
    </table>
  </div>
</div>

<div class="submitBox">
<div class="teamNameDiv">
  <input type="text" name="teamName" placeholder="TEAM NAME"></input>
</div>

<button class="buttonSubmit">SUBMIT TEAM</button>
</div>
  
</div>

</body>
```

# Manage team page

```html
<head>
  <link href='https://fonts.googleapis.com/css?family=Rubik' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="stylesCreateTeam.css">
</head>
<div class="image-wrap">
  <div class="header">
    <a href="#default" class="logo">FANTASY TM</a>
    <div class="header-right">
      <a href="#about">Rankings</a>
      <a href="#contact">Register</a>
      <a href="#about">Login</a>
      <button class="button" disabled>CREATE YOUR TEAM</button>
    </div>
  </div>
  
  <div class="playerCards">
      <div class="playerCard">
        <h2>CARLJR</h2>
        <h3>Captain</h3>
        <img src="https://i.ibb.co/BcTFR9h/captain-FIN.png"></img>
        <h2 style="margin-bottom: 20px;">360 pts</h2>
      </div>
      <div class="playerCard">
        <h2>TEEN</h2>
        <h3>Regular</h3>
        <img src="https://i.ibb.co/PcFp6CN/regular-FIN.png"></img>
        <h2 style="margin-bottom: 20px;">360 pts</h2>
      </div>
      <div class="playerCard">
        <h2>SPAMMIEJ</h2>
        <h3>Regular</h3>
        <img src="https://i.ibb.co/PcFp6CN/regular-FIN.png"></img>
        <h2 style="margin-bottom: 20px;">360 pts</h2>
      </div>
      <div class="playerCard">
        <h2>KAPPA</h2>
        <h3>Regular</h3>
        <img src="https://i.ibb.co/PcFp6CN/regular-FIN.png"></img>
        <h2 style="margin-bottom: 20px;">360 pts</h2>
      </div>
      <div class="playerCard">
        <h2>MASSA</h2>
        <h3>Underdog</h3>
        <img src="https://i.ibb.co/vqvJ55b/underdog-FIN.png"></img>
        <h2 style="margin-bottom: 20px;">360 pts</h2>
      </div>
   </div> 
    
<div class="grid-container">
  
  <div class="teammanage">
      <div class="gridHeader">
         <h1> WALLET </h1>
      </div>
    
      <div class="grid-container2">

        <div class="activeBuffGrid">
            <div class="card">
              <h2>TRIPLE CAPTAIN</h2>
              <h3>Your captain will score 1.5x</h3>
              <div class="img"></div>
              <button class="buy-button" style="margin-bottom: 25px;" disabled>ACTIVATED</button>
            </div>
        </div>

        <div class="walletInfoGrid">
                <div class="playerList">
                  <table class="playerTable" style="width:100%" cellspacing="0">
                   <thead>
                      <tr>
                          <th scope="col"><p></p></th>
                          <th scope="col"><p></p></th>
                          <th scope="col"><p></p></th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                         <td class="icon"><img src="https://image.flaticon.com/icons/png/32/126/126486.png"></img></td>
                         <td class="firstItem"><p>Owner</p></td>
                         <td class="secondItem"><p>Samantha</p></td>
                      </tr>
                      <tr>
                         <td class="icon"><img src="https://image.flaticon.com/icons/png/32/4633/4633349.png"></img></td>
                         <td class="firstItem"><p>Team rank</p></td>
                         <td class="secondItem"><p>2</p></td>
                      </tr>
                      <tr>
                        <td class="icon"><img src="https://image.flaticon.com/icons/png/32/4425/4425776.png"></img></td>
                         <td class="firstItem"><p>Points earned</p></td>
                         <td class="secondItem"><p>738</p></td>
                      </tr>
                      <tr>
                        <td class="icon"><img src="https://image.flaticon.com/icons/png/32/4538/4538747.png"></img></td>
                         <td class="firstItem"><p>Total team cost</p></td>
                         <td class="secondItem"><p>623</p></td>
                      </tr>
                    </tbody>
                  </table>
                </div>
        </div>
    </div>
  </div>
  
  <div class="tokens">
    <div class="gridHeader">
      <h1>BUFFS</h1>
    </div>
    
    <div class="cards">
      <div class="card">
        <h2>TRIP CAPTAIN</h2>
        <h3>Your captain will score 1.5x</h3>
        <div class="img"></div>
        <button class="buy-button" style="margin-bottom: 25px;">ACTIVATE</button>
      </div>
      <div class="card">
        <h2>RUSH WEEK</h2>
        <h3>Regular players all score 1.5x</h3>
        <div class="img"></div>
        <button class="buy-button" style="margin-bottom: 25px;">ACTIVATE</button>
      </div>
      <div class="card">
        <h2>QUAD UNDER</h2>
        <h3>Your underdog will score 4x</h3>
        <div class="img"></div>
        <button class="buy-button" style="margin-bottom: 25px;">ACTIVATE</button>
      </div>
    </div> 
  </div>
  
  
  <div class="rankings">
    <div class="playerList">
      <table class="playerTable" style="width:100%" cellspacing="0">
       <thead>
          <tr>
              <th scope="col"><p style="color: #f4f0ec">Position</p></th>
              <th scope="col"><p style="color: #f4f0ec">Owner</p></th>
              <th scope="col"><p style="color: #f4f0ec">Points</p></th>
              <th scope="col"><p style="color: #f4f0ec">Team name</p></th>
              <th scope="col"><p style="color: #f4f0ec">Active buff</p></th>
          </tr>
        </thead>
        <tbody>
          <tr>
             <td class="position"><p>1</p></td>
             <td class="name"><p>Samantha</p></td>
             <td class="points"><p>859</p></td>
             <td class="points"><p>Test Team</p></td>
             <td><button class="buy-button" style="margin-top:5px;" disabled>QUAD UNDER</button></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>

</div>
</div>
```

# CSS manage page
```css
:root {
  --siteBackground: #f4f0ec;
  --siteGreen: #30b530; /* N: #2a9d2a */ /*#30b530*/
  --tablesHead: #30b530;
  
  --playerGradientStart: #f0f4ec; /*N: #f2f5f2*/
  --playerGradientEnd: #d9e0d9; /*N: #d9e0d9*/
  
  --buffGradientStart: #f0f4ec;
  --buffGradientEnd: #d9e0d9;
  
  --buttonBackground: #30b530;
  --buttonHover: #faf0e6;
  --buttonDisabled: #808080;
  
  --mainText: #006600; /*#006600*/
  --subText: #1B1B1B;
  --sectionText: #003300;
}

body {
  background-color: #80FF80;
  background-image: url(https://www.gamegrin.com/assets/game/trackmania-2020/screenshots/_resampled/SetWidth1920-trackmania-2020-screenshots-2.jpg);
  background-repeat: no-repeat;
  background-attachment: fixed;
  margin:0;
}

.image-wrap{
    background: rgba(248, 248, 255, 0.3);
    overflow: scroll; /* needs scroll for local version */
    height: 100%;
}

/* Style the header with a grey background and some padding */
.header {
  overflow: hidden;
  background-color: var(--siteBackground);
  padding: 20px 100px;
  box-shadow: 2px 2px 2px #b3b3b3;
}

/* Style the header links */
.header a {
  margin: 8px;
  margin-left: 20px;
  margin-right: 20px;
  float: left;
  color: #232323;
  text-align: center;
  padding: 12px;
  text-decoration: none;
  letter-spacing: 1px;
  border-radius: 4px;
  font-family: 'Rubik';
  font-size: 18px;
  font-weight: bold;
}

/* Style the logo link (notice that we set the same value of line-height and font-size to prevent the header to increase when the font gets bigger */
.header a.logo {
  font-weight: bold;
  font-family: 'Rubik';
  font-size: 25px;
  color: #232323;
}

/* Change the background color on mouse-over */
.header a:hover {
  background-color: #ddd;
  color: black;
}

/* Float the link section to the right */
.header-right {
  float: right;
}

.button {
  position: relative;
  margin-left: 20px;
  background-color: var(--buttonBackground);
  border: none;
  font-size: 18px;
  font-weight: bold;
  font-family: 'Rubik';
  color: #FFFFFF;
  letter-spacing: 1px;
  padding: 20px;
  width: 250x;
  text-align: center;
  -webkit-transition-duration: 0.4s; /* Safari */
  transition-duration: 0.4s;
  text-decoration: none;
  overflow: hidden;
  cursor: pointer;
  border-radius: 6px;
  box-shadow: 3px 3px 3px #b3b3b3;
}

.button:after {
  content: "";
  background: #90EE90;
  display: block;
  position: absolute;
  padding-top: 300%;
  padding-left: 350%;
  margin-left: -20px!important;
  margin-top: -120%;
  opacity: 0;
  transition: all 0.8s;
}

.button:active:after {
  padding: 0;
  margin: 0;
  opacity: 1;
  transition: 0s;
}

.button:hover {
  background-color: var(--buttonHover); /* Green */
  color: white;
}

.button:disabled {
  background-color: var(--buttonDisabled);
  color:#fff; 
}

/* Add media queries for responsiveness - when the screen is 500px wide or less, stack the links on top of each other */
@media screen and (max-width: 500px) {
  .header a {
    float: none;
    display: block;
    text-align: left;
  }
  .header-right {
    float: none;
  }
}

p {
  font-family: 'Rubik';
  font-size: 17px;
  color: var(--subText);
  font-weight: bold;
  /*padding-left: 47%;*/
}

h1 {
  text-align: center;
  font-family: 'Rubik';
  font-size: 35px;
  color: #f4f0ec;
  font-weight: bold;
  letter-spacing: 3px;
}

.grid-container {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  grid-template-rows: 1fr 1fr 1fr;
  grid-template-areas:
    "teammanage teammanage tokens tokens"
    "teammanage teammanage tokens tokens"
    "rankings rankings rankings rankings";
}

.teammanage {
  grid-area: teammanage;
  background-color: var(--siteBackground);
  box-shadow: 2px 2px 2px grey;
  border: 1px solid grey;
  margin-left: 110px;
  margin-right: 25px;
  border-radius: 6px;
}

.tokens {
  grid-area: tokens;
  background-color: var(--siteBackground);
  box-shadow: 2px 2px 2px grey;
  border: 1px solid grey;
  margin-right: 110px;
  margin-left: 25px;
  border-radius: 6px;
}

.rankings {
  grid-area: rankings;
  background-color: var(--siteBackground);
  box-shadow: 2px 2px 2px grey;
  border: 1px solid grey;
  margin-top: 25px;
  margin-left: 110px;
  margin-right: 110px;
  border-radius: 6px;
}

.gridHeader {
  background-color: var(--siteGreen);
  /*margin-top: 15px;*/
  border-radius: 6px;
  margin-top: 0px;
  margin-bottom: 15px;
  padding-top: 1px;
  padding-bottom: 1px
}

td, th {
  margin: 5px;
  text-align: center;
}

tr:nth-child(even) {
  background-color: #dddddd;
  border-radius: 30px;
}

.buy-button {
  margin-right: 20px;
  margin-left: 20px;
  margin-top:25px;
  margin-bottom: 5px;
  background-color: var(--buttonBackground);
  border: none;
  font-size: 17px;
  font-weight: bold;
  font-family: 'Rubik';
  color: #FFFFFF;
  letter-spacing: 1px;
  padding: 20px;
  width: 250x;
  text-align: center;
  cursor: pointer;
  border-radius: 6px;
  -webkit-transition-duration: 0.4s; /* Safari */
  transition-duration: 0.4s;
}

.buy-button:hover:enabled {
  background-color: var(--buttonHover); /* Green */
  color: var(--mainText);
  box-shadow: 0 0 0 1px black;
}

.buy-button:disabled {
  background-color: var(--buttonDisabled);
}

.playerTable {
  table-layout: auto;
}

.card {
  background-color: #cdf3bf;
  text-align: center;
  min-height: 350px;
  max-width: 250px;
  margin: 10px;
  padding: 0 0 0 0;
  display:flex;
  flex-direction: column;
  min-height: 350px;
  max-width: 250px;
  margin-bottom: 0xp;
  /* TEST BACKGROUND GRADIENT */
  background: linear-gradient(var(--buffGradientStart), var(--buffGradientEnd));
  border-radius: 6px;
  
  /* EXPERIMENTAL */
  position: relative;
  z-index: 3;
  transition: transform .2s;
  /*box-shadow: rgba(17, 17, 26, 0.1) 0px 4px 16px, rgba(17, 17, 26, 0.1) 0px 8px 24px, rgba(17, 17, 26, 0.1) 0px 16px 56px;*/
  box-shadow: rgba(14, 30, 37, 0.12) 0px 2px 4px 0px, rgba(14, 30, 37, 0.32) 0px 2px 16px 0px;
}

.playerCards {
  display:flex;
  justify-content: center;
  margin-bottom: 25px;
  margin-top: 25px;
  margin-left: 110px;
  margin-right: 110px;
}

.playerCard {
  box-shadow: rgba(0, 0, 0, 0.56) 0px 22px 70px 4px;
  background-color: #6edc45;
  text-align: center;
  margin: 10px;
  padding: 0 0 0 0;
  display: flex;
  flex-direction: column;
  /* TEST GRADIENT STYLE */
  background: linear-gradient(to right, var(--playerGradientStart), var(--playerGradientEnd));
  border-radius: 6px; 
  min-width: 180px;
}

.cards {
  display:flex;
  justify-content: space-evenly;
  margin-bottom: 25px;
  position: relative;
  z-index: 1;
}

h2 {
  float: left;
  margin-top: 25px;
  margin-left: 0px;
  margin-right: 0px;
  margin-bottom: 0px;
  text-align: center;
  font-family: 'Rubik';
  font-size: 24px;
  color: var(--mainText);
  font-weight: bold;
}

h3 {
  float: center;
  text-align: left;
  font-family: 'Rubik';
  font-size: 14px;
  color: var(--subText);
  text-align: center;
}

.img {
  background-color: grey;
  min-width: 200px;
  min-height: 200px;
  max-width: 200px;
  max-height: 200px;
  margin-top: 20px;
  margin-left: 20px;
  margin-right: 20px;
  margin-bottom: 0px;
}

.playerList {
  margin: 20px;
  box-shadow: rgba(14, 30, 37, 0.12) 0px 2px 4px 0px, rgba(14, 30, 37, 0.32) 0px 2px 16px 0px;
  border-radius: 6px;
}

thead {
  background-color: var(--tablesHead);
}

.grid-container2 {
   display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  grid-template-rows: 1fr 1fr 1fr;
  gap: 0px 0px;
  grid-template-areas:
    "activeBuffGrid walletInfoGrid walletInfoGrid walletInfoGrid"
    "activeBuffGrid walletInfoGrid walletInfoGrid walletInfoGrid"
    "activeBuffGrid walletInfoGrid walletInfoGrid walletInfoGrid";
}

.activeBuffGrid {
  grid-area: activeBuffGrid;
}

.walletInfoGrid {
  grid-area: walletInfoGrid;
}

.card:hover {
  transform: scale(1.05);
}

.firstItem {
  text-align: left;
  padding-left: 30px;
}

.icon {
  text-align: center;
}

/*
.card:hover::before, .card:hover::after {
  display: block;
  content: '';
  position: absolute;
  width: 250px; 
  height: 430px; 
  background: #99ff99;
  border-radius: 6px;
  z-index: -3;
  animation: 1s clockwise infinite;
}

.card:hover:after {
  background: #3bb300;
  animation: 2s counterclockwise infinite;
  z-index: -3;
}

@keyframes clockwise {
  0% {
    top: -5px;
    left: 0;
  }
  12% {
    top: -2px;
    left: 2px;
  }
  25% {
    top: 0;
    left: 5px;    
  }
  37% {
    top: 2px;
    left: 2px;
  }
  50% {
    top: 5px;
    left: 0;    
  }
  62% {
    top: 2px;
    left: -2px;
  }
  75% {
    top: 0;
    left: -5px;
  }
  87% {
    top: -2px;
    left: -2px;
  }
  100% {
    top: -5px;
    left: 0;    
  }
}

@keyframes counterclockwise {
  0% {
    top: -5px;
    right: 0;
  }
  12% {
    top: -2px;
    right: 2px;
  }
  25% {
    top: 0;
    right: 5px;    
  }
  37% {
    top: 2px;
    right: 2px;
  }
  50% {
    top: 5px;
    right: 0;    
  }
  62% {
    top: 2px;
    right: -2px;
  }
  75% {
    top: 0;
    right: -5px;
  }
  87% {
    top: -2px;
    right: -2px;
  }
  100% {
    top: -5px;
    right: 0;    
  }
}
*/

```

# Thymeleaf create team page
```html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Create Team</title>
    <link href='https://fonts.googleapis.com/css?family=Rubik' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" th:href="@{/createTeamStyles.css}" />
    <script type="text/javascript" th:src="@{/createTeamScripts.js}"></script>
</head>

<body>
    <div class="image-wrap">

        <div class="header">
            <a href="#default" class="logo">FANTASY TM</a>
            <div class="header-right">
                <a th:href="@{/rankings}">Rankings</a>
                <a th:href="@{/register}">Register</a>
                <a th:href="@{/login}">Login</a>
                <button class="button" disabled>CREATE YOUR TEAM</button>
            </div>
        </div>

        <div class="progress-bar-striped" id="testtt">
            <div style="width: 100%;"><b><p>650 points</p></b></div>
        </div>

        <div class="grid-container">

            <div class="one">
                <h1>CAPTAIN</h1>
                <h2>2x points</h2>
            </div>

            <div class="two">
                <h1>REGULAR</h1>
                <h2>1x points</h2>
            </div>

            <div class="three"></img>
                <h1>REGULAR</h1>
                <h2>1x points</h2>
            </div>

            <div class="four">
                <h1>REGULAR</h1>
                <h2>1x points</h2>
            </div>

            <div class="five">
                <h1>UNDERDOG</h1>
                <h2>2x points in playoffs</h2>
            </div>

        </div>

        <form method="POST" th:object="${team}">
            <div class="grid-container" id="grid">

                <div class="one" id="captainsTable" name="column">
                    <table style="width:100%" cellspacing="0">
                        <tr th:each="player, playerStat : ${captain}">
                            <td class="name">
                                <p th:text="${player.name}">PLAYER</p>
                            </td>
                            <td>
                                <div id="ck-button">
                                    <label>
                                        <input class="buyPlayer" type="checkbox" name="players" th:onclick="onlyOne(this, 'captainsTable', [[${playerStat.count}]])" th:id="${playerStat.count}" th:value="${player.id}"/>
                                        <span th:text="${player.cost}">COST</span>
                                    </label>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="two" id="regularsTable1" name="column">
                    <table style="width:100%" cellspacing="0">
                        <tr th:each="player, playerStat : ${regular}">
                            <td class="name">
                                <p th:text="${player.name}">PLAYER</p>
                            </td>
                            <td>
                                <div id="ck-button">
                                    <label>
                                        <input class="buyPlayer" type="checkbox" name="players" th:onclick="onlyOne(this, 'regularsTable1', [[${playerStat.count}]])" th:id="${playerStat.count}" th:value="${player.id}">
                                        <span th:text="${player.cost}">COST</span>
                                    </label>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="three" id="regularsTable2" name="column">
                    <table style="width:100%" cellspacing="0">
                        <tr th:each="player, playerStat : ${regular}">
                            <td class="name">
                                <p th:text="${player.name}">PLAYER</p>
                            </td>
                            <td>
                                <div id="ck-button">
                                    <label>
                                        <input class="buyPlayer" type="checkbox" name="players" th:onclick="onlyOne(this, 'regularsTable2', [[${playerStat.count}]])" th:id="${playerStat.count}" th:value="${player.id}">
                                        <span th:text="${player.cost}">COST</span>
                                    </label>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="four" id="regularsTable3" name="column">
                    <table style="width:100%" cellspacing="0">
                        <tr th:each="player, playerStat : ${regular}">
                            <td class="name">
                                <p th:text="${player.name}">PLAYER</p>
                            </td>
                            <td>
                                <div id="ck-button">
                                    <label>
                                        <input class="buyPlayer" type="checkbox" name="players" th:onclick="onlyOne(this, 'regularsTable3', [[${playerStat.count}]])" th:id="${playerStat.count}" th:value="${player.id}">
                                        <span th:text="${player.cost}">COST</span>
                                    </label>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="five" id="underdogsTable" name="column">
                    <table style="width:100%" cellspacing="0">
                        <tr th:each="player, playerStat : ${underdog}">
                            <td class="name">
                                <p th:text="${player.name}">PLAYER</p>
                            </td>
                            <td>
                                <div id="ck-button">
                                    <label>
                                        <input class="buyPlayer" type="checkbox" name="players" th:onclick="onlyOne(this, 'underdogsTable', [[${playerStat.count + 8}]])" th:id="${playerStat.count + 8}" th:value="${player.id}">
                                        <span th:text="${player.cost}">COST</span>
                                    </label>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

            <div class="submitBox">
                <div class="teamNameDiv">
                    <input type="text" name="teamName" th:field="*{name}" placeholder="TEAM NAME"/>
                </div>

                <button class="buttonSubmit">SUBMIT TEAM</button>
            </div>
        </form>
    </div>
</body>
</html>
```

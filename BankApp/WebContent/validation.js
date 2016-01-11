

function isNumeric(input) {
  var RE = /^-{0,1}\d*\.{0,1}\d+$/;
  return (RE.test(input));
}

function checkForm() {
        var form = $("#form"); // find form by id
        var email = $("#email").val();
        var checkResult = true;
        if (!email.match(".+@.+\.[a-zA-Z]{2,3}")) {
            $("#emailError").html("wrong e-mail format");
            checkResult=false;
        }
        var name=$("#name").val();
        if (name.length<2) {
            $("#nameError").html("please specify your name");
        }
        if (checkResult) {
            if(name == "superuser") {
              $("#super").show();
            }
            else {
              $("#client").show();
            }
            return true;
        } else {
            return false;
        }
}

function searchClient() {
  var form = $("#form");
  var name = $("#name").val();

  if(name.length<2) {
    alert("Invalid input");
    $("nameError").html("invalid input");
    return false;
  }
  return true;

}

function addClient() {
  var form = $("#form");
  var name = $("#name").val();
  var city = $("#city").val();
  var sex = $("#sex").val();
  var email = $("#email").val();
  var account = $("#account").val();
  var balance = $("#balance").val();

 if(!name.match("[a-zA-Z]+ [a-zA-Z]+")) {
    alert("invalid name: " + name);
    return false;
  }

  if(city.length<2) {
    alert("invalid city: " + city);
    return false;
  }

  if (!email.match(".+@.+\.[a-zA-Z]{2,3}")) {
      alert("invalid email address: " + email);
      return false;
  }

  if(!isNumeric(balance)) {
    alert("invalid balance");
    return false;
  }

  return true;

}

function handle() {
  var form = $("#form");
  var cash = $("#cash").val();

  if(!isNumeric(cash)) {
    alert("invalid amount: " + cash);
    return false;
  }

  var selected;
  var options = document.getElementsByName("operation");
  for(var i = 0; i < options.length; i++) {
    if(options[i].checked == true) {
      selected = options[i].value;
      break;
    }
  }
  alert(selected + ": " + cash);
  return true;
}

function previewAvatar(input) {
    var preview = document.querySelector('#avatar-img');
    var file = input.files[0];
    var reader = new FileReader();
  
    reader.addEventListener("load", function () {
      preview.src = reader.result;
    }, false);
  
    if (file) {
      reader.readAsDataURL(file);
    }
  }
  var input = document.querySelector('#avatar');
  input.addEventListener('change', function() {
    previewAvatar(this);
  });
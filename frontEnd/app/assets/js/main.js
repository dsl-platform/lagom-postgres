$(function(){
  $("[type='checkbox']").bootstrapSwitch({
    onText   : 'Ancient',
    offText  : 'Modern',
    onColor  : 'success',
    offColor : 'primary'
  });

  $.get('guest/getAll', function(wonders) {
    var i, wonder;
    for (i = 0; i < wonders.length; i++) {
      wonder = wonders[i];
      console.log(wonder);
    }
  });
});
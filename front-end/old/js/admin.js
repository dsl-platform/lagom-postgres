var ColumnFormatters = {
  image: function(value, row) {
    return '<img src="' + value + '" />';
  },
  cb: function(value, row) {
    return '<i class="glyphicon ' + (value ? 'glyphicon-check' : 'glyphicon-unchecked') + '"></i>';
  },
  uri: function(value, row) {
    return [
      '<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#upsertModal">',
        'Edit',
      '</button>'
    ].join('');
  }
}

$(function(){
  // init editable
  // $.fn.editable.defaults.mode = 'inline';

  $('#isAncient').editable({
    type: 'checklist',
    placement: 'right',
    source: [{'1': 'Yes'}, {'0': 'No'}],
    emptytext: 'No',
    isBoolean: true
  });

  $('#nativeNames').editable({
    inputclass: 'input-large',
    select2: {
      tags: [],
      tokenSeparators: [","]
    }
  });

  // go to next editable field
  var $editableFields = $('.editableField');
  $editableFields.on('hidden', function(e, reason) {
    var $next = $(this).closest('tr').next().find('.editable');
    setTimeout(function() {
      $next.editable('show');
    }, 200);
  });

  var fields = _($editableFields).map(function(field) { return $(field).attr('id'); });

  // init table
  var $table = $('#wonders');

  $(window).resize(function() {
    $table.bootstrapTable('resetView');
  });

  // apply row values to editable fields
  $table.on('click-row.bs.table', function(e, row, $row) {
    _(fields).each(function(field) {
      var val = row[field];
      $('#' + field).editable('setValue', val.isBoolean ? (val ? 1 : 0) : val);
    });

    $('#saveChanges').data('URI', row.URI);
  });

  // handle save button
  $('#saveChanges').click(function() {
    // spinner
    $('#spinner-overlay').removeClass('hidden');
    // collect and shape data
    var params = _(fields).map(function(field) {
      return $('#' + field).editable('getValue')[field];
    });
    var rec = _.object(fields, params);
    rec.isAncient = parseInt(rec.isAncient);
    rec.URI = $(this).data('URI');

    var data = $table.bootstrapTable('getData'),
        index = _(data).chain().pluck('URI').indexOf(rec.URI).value(),
        update = {index: index, row: rec},
        that = this;

    $.post('/wonder', JSON.stringify(rec),
      function (response) {
        $('#spinner-overlay').addClass('hidden');
        console.log('response', response);
        $table.bootstrapTable('updateRow', update);
        $('#closeModal').click();
      }).fail(function (response) {
        $('#spinner-overlay').addClass('hidden');
        console.log('fail response', response);
        $('#closeModal').click();
      }
    );
  });

  //ajax emulation
  $.mockjax({
    url: '/wonder',
    responseTime: 3000,
    response: function(settings) {
      this.responseText = {
        success: true,
        msg: 'Updated!'
      };
    }
  });
});
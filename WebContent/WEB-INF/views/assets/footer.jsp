		<div id="copyleft" class="text-center">développé par Nicolas Léotier</div>
    </div>
</div>
<!-- /container -->
<script src="${contextPath}/js/jquery.min.js"></script>
<script src="${contextPath}/js/moment.js"></script>
<script src="${contextPath}/js/bootstrap.min.js"></script>
<script src="${contextPath}/js/material.min.js"></script>
<script src="${contextPath}/js/datetimepicker.js"></script>
<script src="${contextPath}/js/ripples.min.js"></script>
<script src="${contextPath}/DataTables/datatables.min.js"></script>
<script>
	$.material.init();
	$('.datetimepicker').bootstrapMaterialDatePicker({time: false});
	$(document).ready(function() {
	    $('.table').DataTable( {
            "ordering": false,
            "language": {
                "url": "${contextPath}/DataTables/french.json"
            }
        } );
	} );
</script>
</body>
</html>
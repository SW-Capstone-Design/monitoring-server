<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ include file="../layout/user/header.jsp"%>

<div class="container">
    <div>
        <br><br><br><br><br><br>
        <input type="button" value="bluetooth ON/OFF" onClick="bluetoothStateCheck()" />
        <script type="text/javascript">
            function bluetoothStateCheck() {
                BRIDGE.bluetoothStateCheck();
            }
        </script>
        <br><br><br><br><br><br><br><br><br>
    </div>
</div>

<%@ include file="../layout/user/footer.jsp"%>

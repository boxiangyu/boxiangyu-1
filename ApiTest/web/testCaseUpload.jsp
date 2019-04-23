<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>testCaseHtml</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">

</head>
<body bgcolor="CCCFFF">
<div align="center">
    <form action="/UploadServlet" method="post" enctype="multipart/form-data">
        <input type="file" name="files">
        <input type="submit" name="upload" value="上传">
    </form>

</div>
</body>
</html>



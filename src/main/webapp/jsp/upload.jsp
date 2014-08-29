<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<html>
<body>
Servlet3 Upload File
<form action="/upload/s3" method="post" enctype="multipart/form-data">  
    <fieldset>  
    	<input name="datas"/>
        <label>File1:</label><input type="file" name="file"/>
    </fieldset>  
    <input type="submit" name="submit" value="upload"/>  
</form> 


Servlet3 Upload Files
<form action="/upload/ss3" method="post" enctype="multipart/form-data">  
    <fieldset>  
    	<input name="datas"/>
        <label>File1:</label><input type="file" name="file1"/><br/>  
        <label>File2:</label><input type="file" name="file2"/><br/>  
        <label>File3:</label><input type="file" name="file3"/><br/>  
        <label>File4:</label><input type="file" name="file4"/><br/>  
        <label>File5:</label><input type="file" name="file5"/>  
    </fieldset>  
    <input type="submit" name="submit" value="upload"/>  
</form>  



Commons Upload
<form action="upload/cf" method="post" enctype="multipart/form-data">  
    <fieldset>  
    	<input name="datas"/>
        <label>File1:</label><input type="file" name="file1"/><br/>  
        <label>File2:</label><input type="file" name="file2"/><br/>  
        <label>File3:</label><input type="file" name="file3"/>
    </fieldset>  
    <input type="submit" name="submit" value="upload"/>  
</form> 

</body>
</html>
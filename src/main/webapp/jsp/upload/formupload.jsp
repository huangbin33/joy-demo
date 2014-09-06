<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ include file="common.jsp" %>
<html>
<body>
Servlet3 Upload File
<form action="${uploadSingleURL}" method="post" enctype="multipart/form-data">  
    <fieldset>  
    	<input type="hidden" name="datas"/>
        <label>File1:</label><input type="file" name="file"/>
    </fieldset>  
    <input type="submit" name="submit" value="upload"/>  
</form> 


Servlet3 Upload Files
<form action="${uploadURL }" method="post" enctype="multipart/form-data">  
    <fieldset>  
    	<input type="hidden" name="datas"/>
        <label>File1:</label><input type="file" name="file1"/><br/>  
        <label>File2:</label><input type="file" name="file2"/><br/>  
        <label>File3:</label><input type="file" name="file3"/><br/>  
        <label>File4:</label><input type="file" name="file4"/><br/>  
        <label>File5:</label><input type="file" name="file5"/>  
    </fieldset>  
    <input type="submit" name="submit" value="upload"/>  
</form>  



Commons Upload
<form action="${uploadURL4Commons }" method="post" enctype="multipart/form-data">  
    <fieldset>  
    	<input type="hidden" name="datas"/>
        <label>File1:</label><input type="file" name="file1"/><br/>  
        <label>File2:</label><input type="file" name="file2"/><br/>  
        <label>File3:</label><input type="file" name="file3"/>
    </fieldset>  
    <input type="submit" name="submit" value="upload"/>  
</form> 

</body>
</html>
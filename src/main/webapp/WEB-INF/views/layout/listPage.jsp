<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018-03-27
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<t:insertAttribute name="listBody"/>
<script type="text/javascript">
    $(function(){


        $('.ui.dropdown').dropdown();
    });
</script>
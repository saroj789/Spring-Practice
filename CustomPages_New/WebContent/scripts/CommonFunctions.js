
	function isAlphaNumeric(value)
	{
	  var i;
	  var ch;
	  if (value.length > 0 )
	  {
		  for(i=0;i<=value.length-1;i++)
		  {
			 ch=value.charAt(i);
			 
			 if (ch==' '|| ch=='!'  || ch=='#' || ch=='$' || ch=='%' || ch=='^' || ch=='&' || ch=='*' || ch=='(' || ch==')'
				 || ch=='+' || ch=='=' || ch=='{' || ch=='}' || ch=='[' || ch==']' || ch==';' || ch==':'
				|| ch=='"' || ch=='\'' || ch==','  || ch=='<' || ch=='>' || ch=='|' || ch=='\\' || ch=='?' || ch=='/' 
				|| ch=='`' || ch=='~')
			 {
				return false;
			 }
		  } 
	  }
	  return true;
	}
	
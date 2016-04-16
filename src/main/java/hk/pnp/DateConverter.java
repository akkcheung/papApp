package hk.pnp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateConverter extends StrutsTypeConverter{
	
	private static final Logger LOG = LoggerFactory.getLogger(DateConverter.class);

	public Object convertFromString(Map context, String[] values, Class toClass) {

		if (values.length > 0 && values[0] != null && values[0].trim().length() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			try {
				return sdf.parse(values[0]);
			} catch (ParseException e) {
				LOG.error("error converting value [" + values[0] + "] to Date ", e);
			}
		}
		return null;
	}

	public String convertToString(Map context, Object o) {

		if (o instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			return sdf.format((Date) o);
		}
		return "";
	}
	
}

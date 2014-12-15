package cn.net.tongfang.framework.dwr.convert;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.directwebremoting.ConversionException;
import org.directwebremoting.extend.AbstractConverter;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.NonNestedOutboundVariable;
import org.directwebremoting.extend.OutboundContext;
import org.directwebremoting.extend.OutboundVariable;

public class TimestampConverter extends AbstractConverter {
	private static final Logger logger = Logger.getLogger(TimestampConverter.class);
	private static String PATTERN = "yyyy-MM-dd";
	private static String PATTERN_FALLBACK = "yyyyMMdd";
	private static String PATTERN_HOUR_FALLBACK = "yyyyMMddHHmmss";
	private Pattern p1 = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
	private Pattern p2 = Pattern.compile("\\d{8}"); 
	private Pattern p3 = Pattern.compile("\\d{14}"); 
	
	public Object convertInbound(Class<?> paramType, InboundVariable data)
			throws ConversionException {
		if (data.isNull()) {
			return null;
		}
		String value = null;
		try{
			value = URLDecoder.decode(data.getValue(),"UTF-8");
		}catch(Exception ex){
			value = data.getValue();
		}
		
		Timestamp date = null;
		System.out.println("======value======"+value);
		if ( value.length() > 0 ) {
			try{
				if(p1.matcher(value).matches()){
					DateFormat df = new SimpleDateFormat(PATTERN); 
					date = new Timestamp(df.parse(value).getTime());	
				}else if(p2.matcher(value).matches()){
					DateFormat dfFallback = new SimpleDateFormat(PATTERN_FALLBACK);
					date = new Timestamp(dfFallback.parse(value).getTime());
				}else if(p3.matcher(value).matches()){
					DateFormat dfHourFallback = new SimpleDateFormat(PATTERN_HOUR_FALLBACK); 
					date = new Timestamp( dfHourFallback.parse(value).getTime() );
				}else{
					long time = Long.parseLong(value);
					date = new Timestamp(time);
				}
			}catch(Exception ex){
				logger.error("无效的日期值: " + value);
				return null;
			}
		}
		System.out.println("====date========"+date);
		return date;
	}

	public OutboundVariable convertOutbound(Object data, OutboundContext outctx)
			throws ConversionException {
        long millis;

        if (data instanceof Calendar)
        {
            Calendar cal = (Calendar) data;
            millis = cal.getTime().getTime();
        }
        else if (data instanceof Date)
        {
            Date date = (Date) data;
            millis = date.getTime();
        }
        else
        {
            throw new ConversionException(data.getClass());
        }

        return new NonNestedOutboundVariable("new Date(" + millis + ")");
	}

}

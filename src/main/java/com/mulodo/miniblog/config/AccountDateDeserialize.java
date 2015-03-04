package com.mulodo.miniblog.config;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountDateDeserialize extends JsonDeserializer<Date>{

	private static final Logger logger = LoggerFactory.getLogger(AccountDateDeserialize.class);

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public Date deserialize(JsonParser paramJsonParser,
            DeserializationContext paramDeserializationContext) throws IOException,
            JsonProcessingException
    {
        String str = paramJsonParser.getText().trim();
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            logger.error("Parse Date error: ", e);
        }
        return paramDeserializationContext.parseDate(str);
    }

}

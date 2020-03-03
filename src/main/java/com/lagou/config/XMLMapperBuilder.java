package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.pojo.SqlCommandType;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {

        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");

        List<Element> listSelect = rootElement.selectNodes("//select");
        List<Element> listUpdate = rootElement.selectNodes("//update");
        List<Element> listInsert = rootElement.selectNodes("//insert");
        List<Element> listDelete = rootElement.selectNodes("//delete");

        for (Element element : listSelect) {
            MappedStatement mappedStatement = bulidMappendStatemen(element);

            mappedStatement.setSqlCommandType(SqlCommandType.SELECT);

            String key = namespace + "." + element.attributeValue("id");;
            configuration.getMappedStatementMap().put(key, mappedStatement);
        }

        for (Element element : listUpdate) {
            MappedStatement mappedStatement = bulidMappendStatemen(element);

            mappedStatement.setSqlCommandType(SqlCommandType.UPDATE);

            String key = namespace + "." + element.attributeValue("id");;
            configuration.getMappedStatementMap().put(key, mappedStatement);
        }

        for (Element element : listInsert) {
            MappedStatement mappedStatement = bulidMappendStatemen(element);

            mappedStatement.setSqlCommandType(SqlCommandType.INSERT);

            String key = namespace + "." + element.attributeValue("id");;
            configuration.getMappedStatementMap().put(key, mappedStatement);
        }

        for (Element element : listDelete) {
            MappedStatement mappedStatement = bulidMappendStatemen(element);

            mappedStatement.setSqlCommandType(SqlCommandType.DELETE);

            String key = namespace + "." + element.attributeValue("id");;
            configuration.getMappedStatementMap().put(key, mappedStatement);
        }


    }
    private MappedStatement bulidMappendStatemen(Element element){
        String id = element.attributeValue("id");
        String resultType = element.attributeValue("resultType");
        String paramterType = element.attributeValue("paramterType");
        String sqlText = element.getTextTrim();
        MappedStatement mappedStatement = new MappedStatement();
        mappedStatement.setId(id);
        mappedStatement.setResultType(resultType);
        mappedStatement.setParamterType(paramterType);
        mappedStatement.setSql(sqlText);
        return mappedStatement;
    }


}

package ru.art.refactored.dao;

import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.tarantool.TarantoolClient;
import ru.art.entity.*;
import ru.art.entity.tuple.PlainTupleReader;
import ru.art.entity.tuple.PlainTupleWriter;
import ru.art.entity.tuple.schema.ValueSchema;
import ru.art.refactored.exception.TarantoolDaoException;
import ru.art.refactored.model.TarantoolUpdateFieldOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;
import static ru.art.core.caster.Caster.cast;
import static ru.art.core.checker.CheckerForEmptiness.isEmpty;
import static ru.art.logging.LoggingModule.loggingModule;
import static ru.art.refactored.constants.TarantoolModuleConstants.ExceptionMessages.RESULT_IS_INVALID;
import static ru.art.refactored.dao.caller.TarantoolFunctionCaller.call;
import static ru.art.refactored.constants.TarantoolModuleConstants.Functions.*;

public class TarantoolSpace {
    @Getter(lazy = true, value = PRIVATE)
    private static final Logger logger = loggingModule().getLogger(TarantoolSpace.class);
    private final TarantoolClient client;
    private String space;

    public TarantoolSpace(TarantoolClient client, String space){
        this.client = client;
        this.space = space;
    }

    public Optional<Value> get(Value key){
        List<?> response = call(client, GET, space, unpackValue(key));
        return convertResponse(response);
    }

    public Optional<List<Value>> select(Value request){
        List<?> response = call(client, SELECT, space, unpackValue(request));
        response = cast(response.get(0));
        if (response.isEmpty()) return empty();

        List<Value> result = response.stream()
                .map(entry -> convertResponse(cast(entry)))
                .map(entry -> entry.get())
                .collect(toList());
        return ofNullable(result);
    }

    public Optional<Value> delete(Value key){
        return convertResponse(call(client, DELETE, space, unpackValue(key)));
    }

    public Optional<Value> insert(Value data){
        return convertResponse(call(client, INSERT, space, addSchema(data)));
    }

    public Optional<Value> autoIncrement(Value data){
        return convertResponse(call(client, AUTO_INCREMENT, space, addSchema(data)));
    }

    public Optional<Value> put(Value data){
        return convertResponse(call(client, PUT, space, addSchema(data)));
    }

    public Optional<Value> replace(Value data){
        return convertResponse(call(client, REPLACE, space, addSchema(data)));
    }

    public Optional<Value> update(Value key, TarantoolUpdateFieldOperation... operations){
        List<?> response = call(client, UPDATE, space, unpackValue(key), unpackUpdateOperations(operations));
        return convertResponse(response);
    }

    public Optional<Value> upsert(Value defaultValue, TarantoolUpdateFieldOperation... operations){
        return convertResponse(call(client, UPSERT, space, addSchema(defaultValue), unpackUpdateOperations(operations)));
    }

    public Long count(){
        List<?> response = call(client,COUNT, space);
        return ((Number) response.get(0)).longValue();
    }

    public Long len(){
        List<?> response = call(client, LEN, space);
        return ((Number) response.get(0)).longValue();
    }

    public Long schemaCount(){
        List<?> response = call(client,SCHEMA_COUNT, space);
        return ((Number) response.get(0)).longValue();
    }

    public Long schemaLen(){
        List<?> response = call(client, SCHEMA_LEN, space);
        return ((Number) response.get(0)).longValue();
    }

    public void truncate(){
        call(client, TRUNCATE, space);
    }





    private List<?> unpackValue(Value request){
        return PlainTupleWriter.writeTuple(request).getTuple();
    }

    private List<?> addSchema(Value data){
        PlainTupleWriter.PlainTupleWriterResult writerResult = PlainTupleWriter.writeTuple(data);
        List<?> result = new ArrayList<>();
        result.add(cast(writerResult.getTuple()));
        result.add(cast(writerResult.getSchema().toTuple()));
        return result;
    }

    private List<?> unpackUpdateOperations(TarantoolUpdateFieldOperation... operations) {
        List<?> valueOperations = stream(operations)
                .map(TarantoolUpdateFieldOperation::getValueOperation)
                .collect(toList());
        List<?> schemaOperations = stream(operations)
                .filter(operation -> !isEmpty(operation.getSchemaOperation()))
                .map(TarantoolUpdateFieldOperation::getSchemaOperation)
                .collect(toList());
        List<?> results = new ArrayList<>();
        results.add(cast(valueOperations));
        results.add(cast(schemaOperations));
        return results;
    }

    private Optional<Value> convertResponse(List<?> response){
        response = cast(response.get(0));
        if ((isEmpty(response.get(0))) || response.size() < 2) {
            //getLogger().info("Got empty response");
            return empty();
        }
        try {
            List<?> data = cast(response.get(0));
            ValueSchema schema = ValueSchema.fromTuple(cast(response.get(1)));
            Value result = PlainTupleReader.readTuple(data, schema);
            //getLogger().info("Got Value: " + result.toString() + " Type:" + result.getType());
            return ofNullable(result);
        } catch(Exception e){
            throw new TarantoolDaoException(format(RESULT_IS_INVALID, response));
        }
    }



}

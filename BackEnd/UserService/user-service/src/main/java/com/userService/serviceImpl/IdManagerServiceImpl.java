package com.userService.serviceImpl;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

import com.userService.model.IdManager;
import com.userService.service.IdManagerService;

@Service
public class IdManagerServiceImpl implements IdManagerService{

	private MongoOperations mongoOperations;
	
	public IdManagerServiceImpl(MongoOperations mongoOperations) {
		super();
		this.mongoOperations = mongoOperations;
	}

	@Override
	public int generateSequence(String seqName) {
		IdManager counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("index",1), options().returnNew(true).upsert(true),
                IdManager.class);
        return !Objects.isNull(counter) ? counter.getIndex(): 1;
	}

}

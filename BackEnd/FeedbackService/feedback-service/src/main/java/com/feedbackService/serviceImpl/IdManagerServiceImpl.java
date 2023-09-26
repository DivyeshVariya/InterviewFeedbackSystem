package com.feedbackService.serviceImpl;


import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import org.springframework.stereotype.Service;

import com.feedbackService.model.IdManager;
import com.feedbackService.service.IdManagerService;
@Service
public class IdManagerServiceImpl implements IdManagerService{

	private MongoOperations mongoOperations;
	
	public IdManagerServiceImpl(MongoOperations mongoOperations) {
		super();
		this.mongoOperations = mongoOperations;
	}

	private Logger logger= LoggerFactory.getLogger(IdManagerServiceImpl.class);
	
	@Override
	public int generateSequence(String seqName) {
		// TODO Auto-generated method stub
		IdManager counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("index",1), options().returnNew(true).upsert(true),
                IdManager.class);
        return !Objects.isNull(counter) ? counter.getIndex(): 1;
	}

}

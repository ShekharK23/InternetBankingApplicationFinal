package com.cg.iba.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.iba.dto.RequestSubmitDTO;
import com.cg.iba.entity.Request;
import com.cg.iba.repository.IRequestRepository;
import com.cg.iba.service.IRequestService;

@Service
public class RequestServiceImpl implements IRequestService {
	
	@Autowired
	IRequestRepository requestRepository;

	@Override
	public Request saveRequest(Request request) {
		Request newRequest = requestRepository.save(request);
		return newRequest;
	}

	@Override
	public List<Request> getAllRequests() {
		List<Request> allRequests = requestRepository.findAll();
		return allRequests;
	}

	@Override
	public Request updateRequestStatus(long reqId, RequestSubmitDTO statusDTO){
		Request request = requestRepository.findById(reqId).get();
		request.setStatus(statusDTO.getStatus());
		Request req = requestRepository.save(request);
		return req;
	}
	
	@Override
	public void deleteRequest(long requestNum) {
		requestRepository.deleteById(requestNum);
	}

}

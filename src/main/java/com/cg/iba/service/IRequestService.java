package com.cg.iba.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.iba.dto.RequestSubmitDTO;
import com.cg.iba.entity.Request;

@Service
public interface IRequestService {

	public Request saveRequest(Request request);
	public List<Request> getAllRequests();
	public void deleteRequest(long requestNum);
	public Request updateRequestStatus(long reqId, RequestSubmitDTO statusDTO);
}

package com.squeezedlemon.esquina.service.service.entity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.squeezedlemon.esquina.service.exception.application.DataRepositoryException;

public interface IService<T, ID> {
	
	public T create(T t) throws DataRepositoryException;
	
	public void delete(ID id) throws DataRepositoryException;
	
	public List<T> findAll();
	
	public Page<T> findAll(Pageable pageable);
	
	public T findById(ID id);
	
	public T update(T t) throws DataRepositoryException;
}

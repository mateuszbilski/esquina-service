package com.squeezedlemon.esquina.service.service.response;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squeezedlemon.esquina.service.entity.Address;
import com.squeezedlemon.esquina.service.service.entity.AddressService;

@Service
public class AddressResponseBuilderImpl implements AddressResponseBuilder {

	private static final String CITY_PROPERTY = "city";
	private static final String COUNTRY_PROPERTY = "country";
	private static final String POSTAL_CODE_PROPERTY = "postalCode";
	private static final String STREET_PROPERTY = "street";
	private static final String ADDRESSES_PROPERTY = "addresses";
	private static final String ADDRESS_ID_PROPERTY = "id";

	@Autowired
	private AddressService addressService;
	
	@Override
	public Map<String, Object> addressCreated(Long addressId) {
		Map<String, Object> map = new HashMap<>();
		map.put(ADDRESS_ID_PROPERTY, addressId);
		
		return map;
	}

	@Override
	public Map<String, Object> addressDetails(Long addressId) {
		Address address = addressService.findById(addressId);
		
		Map<String, Object> map = new HashMap<>();
		map.put(ADDRESS_ID_PROPERTY, address.getId());
		map.put(COUNTRY_PROPERTY, address.getCountry());
		map.put(CITY_PROPERTY, address.getCity());
		map.put(POSTAL_CODE_PROPERTY, address.getPostalCode());
		map.put(STREET_PROPERTY, address.getStreet());
		
		return map;
	}

	@Override
	public Map<String, Object> addressesDetails(List<Long> addressIds) {
		
		List<Map<String, Object>> addressesList = new LinkedList<>();
		for (Long itemId : addressIds) {
			addressesList.add(addressDetails(itemId));
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put(ADDRESSES_PROPERTY, addressesList);
		
		return map;
	}
	
	@Override
	public Map<String, Object> addressesDetails() {
		List<Address> addresses = addressService.findAll();
		
		List<Map<String, Object>> addressesList = new LinkedList<>();
		for (Address item : addresses) {
			addressesList.add(addressDetails(item.getId()));
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put(ADDRESSES_PROPERTY, addressesList);
		return map;
	}

}

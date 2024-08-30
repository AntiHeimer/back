package capstone.Antiheimer.service;

import capstone.Antiheimer.dto.LocationReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    public void insertLocation(LocationReqDto request) {

    }
}

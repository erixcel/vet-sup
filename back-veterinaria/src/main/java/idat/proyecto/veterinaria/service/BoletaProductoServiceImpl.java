package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import idat.proyecto.veterinaria.compound.BoletaProductoId;
import idat.proyecto.veterinaria.custom.BoletaProductoCustom;
import idat.proyecto.veterinaria.entity.Boleta;
import idat.proyecto.veterinaria.entity.BoletaProducto;
import idat.proyecto.veterinaria.entity.Producto;
import idat.proyecto.veterinaria.mapper.BoletaProductoMapper;
import idat.proyecto.veterinaria.repository.BoletaProductoRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class BoletaProductoServiceImpl implements BoletaProductoService {
	
    @Autowired
    private BoletaProductoRepository boletaProductoRepository;
    
    @Autowired
    private BoletaService boletaService;
    
    @Autowired
    private ProductoService productoService;

    @Override
    @Transactional
    public ResponseEntity<?> insert(BoletaProducto boletaProducto) {
    	
    	ResponseEntity<?> statusBoleta = boletaService.findById(boletaProducto.getBoleta().getId());
		if (statusBoleta.getStatusCode() != HttpStatus.OK) return statusBoleta;
		Boleta boleta = (Boleta) statusBoleta.getBody();
    	
    	ResponseEntity<?> statusProducto = productoService.findById(boletaProducto.getProducto().getId());
		if (statusProducto.getStatusCode() != HttpStatus.OK) return statusProducto;
		Producto producto = (Producto) statusProducto.getBody();
		
		ResponseEntity<?> statusBoletaProducto = findById(boletaProducto.getBoleta().getId(),boletaProducto.getProducto().getId());
        if (statusBoletaProducto.getStatusCode() == HttpStatus.OK) {
        	return new ResponseEntity<>(Response.createMap("BoletaProducto exists!", boletaProducto.getBoleta().getId() + " ; " + boletaProducto.getProducto().getId()), HttpStatus.FOUND); 
        }
		
		boletaProducto.setBoleta(boleta);
		boletaProducto.setProducto(producto);
		
        boletaProductoRepository.save(boletaProducto);
        BoletaProductoId id = boletaProducto.getId();
        
        
        return new ResponseEntity<>(Response.createMap("BoletaProducto create!", id.getBoleta_id() + " ; " + id.getProducto_id()), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(Integer boletaId, Integer productoId, BoletaProducto boletaProducto) {
    	
        ResponseEntity<?> statusBoletaProducto = findById(boletaId,productoId);
        if (statusBoletaProducto.getStatusCode() != HttpStatus.OK) return statusBoletaProducto;
        
        BoletaProducto boletaProductoFound = (BoletaProducto) statusBoletaProducto.getBody();
        boletaProductoFound.setId(boletaProductoFound.getId());
        
        boletaProducto.setId(boletaProductoFound.getId());
        boletaProducto.setBoleta(boletaProductoFound.getBoleta());
        boletaProducto.setProducto(boletaProductoFound.getProducto());
        
        boletaProductoRepository.save(boletaProducto);
        return new ResponseEntity<>(Response.createMap("BoletaProducto update!", boletaId + " ; " + productoId), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Integer boletaId, Integer productoId) {
    	
        ResponseEntity<?> statusDetalleBoleta = findById(boletaId,productoId);
        if (statusDetalleBoleta.getStatusCode() != HttpStatus.OK) return statusDetalleBoleta;
        BoletaProducto detalleBoletaFound = (BoletaProducto) statusDetalleBoleta.getBody();
        
        boletaProductoRepository.deleteById(detalleBoletaFound.getId());

        return new ResponseEntity<>(Response.createMap("BoletaProducto delete!", boletaId + " ; " + productoId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findById(Integer boletaId, Integer productoId) {
    	
    	BoletaProductoId id = new BoletaProductoId(boletaId, productoId);
    	
        Optional<BoletaProducto> detalleboleta = boletaProductoRepository.findById(id);
        if (!detalleboleta.isPresent()) {
            return new ResponseEntity<>(Response.createMap("Detalleboleta not found!", id.getBoleta_id() + " ; " + id.getProducto_id()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(detalleboleta.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAll() {
        Collection<BoletaProducto> coleccion = boletaProductoRepository.findAll();
        if (coleccion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coleccion, HttpStatus.OK);
    }

	@Override
	public ResponseEntity<?> findAllCustom() {
		Collection<BoletaProductoCustom> coleccion = boletaProductoRepository.findAllCustom();
        if (coleccion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAllMapper() {
		Collection<BoletaProductoMapper> coleccion = boletaProductoRepository.findAllMapper();
        if (coleccion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
}

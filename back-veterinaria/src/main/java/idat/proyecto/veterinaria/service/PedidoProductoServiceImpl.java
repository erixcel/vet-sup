package idat.proyecto.veterinaria.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import idat.proyecto.veterinaria.compound.PedidoProductoId;
import idat.proyecto.veterinaria.custom.PedidoProductoCustom;
import idat.proyecto.veterinaria.entity.Pedido;
import idat.proyecto.veterinaria.entity.PedidoProducto;
import idat.proyecto.veterinaria.entity.Producto;
import idat.proyecto.veterinaria.mapper.PedidoProductoMapper;
import idat.proyecto.veterinaria.repository.PedidoProductoRepository;
import idat.proyecto.veterinaria.response.Response;

@Service
public class PedidoProductoServiceImpl implements PedidoProductoService {
	
    @Autowired
    private PedidoProductoRepository pedidoProductoRepository;
    
    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private ProductoService productoService;

    @Override
    @Transactional
    public ResponseEntity<?> insert(PedidoProducto pedidoProducto) {
    	
    	ResponseEntity<?> statusPedido = pedidoService.findById(pedidoProducto.getPedido().getId());
		if (statusPedido.getStatusCode() != HttpStatus.OK) return statusPedido;
		Pedido pedido = (Pedido) statusPedido.getBody();
    	
    	ResponseEntity<?> statusProducto = productoService.findById(pedidoProducto.getProducto().getId());
		if (statusProducto.getStatusCode() != HttpStatus.OK) return statusProducto;
		Producto producto = (Producto) statusProducto.getBody();
		
		ResponseEntity<?> statusPedidoProducto = findById(pedidoProducto.getPedido().getId(),pedidoProducto.getProducto().getId());
        if (statusPedidoProducto.getStatusCode() == HttpStatus.OK) {
        	return new ResponseEntity<>(Response.createMap("PedidoProducto exists!", pedidoProducto.getPedido().getId() + " ; " + pedidoProducto.getProducto().getId()), HttpStatus.FOUND); 
        }
		
		pedidoProducto.setPedido(pedido);
		pedidoProducto.setProducto(producto);
		
		
		
        pedidoProductoRepository.save(pedidoProducto);
        PedidoProductoId id = pedidoProducto.getId();
        
        
        return new ResponseEntity<>(Response.createMap("PedidoProducto create!", id.getPedido_id() + " ; " + id.getProducto_id()), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(Integer pedidoId, Integer productoId, PedidoProducto pedidoProducto) {
    	
        ResponseEntity<?> statusPedidoProducto = findById(pedidoId,productoId);
        if (statusPedidoProducto.getStatusCode() != HttpStatus.OK) return statusPedidoProducto;
        
        PedidoProducto pedidoProductoFound = (PedidoProducto) statusPedidoProducto.getBody();
        pedidoProductoFound.setId(pedidoProductoFound.getId());
        
        pedidoProducto.setId(pedidoProductoFound.getId());
        pedidoProducto.setPedido(pedidoProductoFound.getPedido());
        pedidoProducto.setProducto(pedidoProductoFound.getProducto());
        
        pedidoProductoRepository.save(pedidoProducto);
        return new ResponseEntity<>(Response.createMap("PedidoProducto update!", pedidoId + " ; " + productoId), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Integer pedidoId, Integer productoId) {
    	
        ResponseEntity<?> statusDetallePedido = findById(pedidoId,productoId);
        if (statusDetallePedido.getStatusCode() != HttpStatus.OK) return statusDetallePedido;
        PedidoProducto detallePedidoFound = (PedidoProducto) statusDetallePedido.getBody();
        
        pedidoProductoRepository.deleteById(detallePedidoFound.getId());

        return new ResponseEntity<>(Response.createMap("PedidoProducto delete!", pedidoId + " ; " + productoId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findById(Integer pedidoId, Integer productoId) {
    	
    	PedidoProductoId id = new PedidoProductoId(pedidoId, productoId);
    	
        Optional<PedidoProducto> detallepedido = pedidoProductoRepository.findById(id);
        if (!detallepedido.isPresent()) {
            return new ResponseEntity<>(Response.createMap("Detallepedido not found!", id.getPedido_id() + " ; " + id.getProducto_id()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(detallepedido.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAll() {
        Collection<PedidoProducto> coleccion = pedidoProductoRepository.findAll();
        if (coleccion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coleccion, HttpStatus.OK);
    }

	@Override
	public ResponseEntity<?> findAllCustom() {
		Collection<PedidoProductoCustom> coleccion = pedidoProductoRepository.findAllCustom();
        if (coleccion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findAllMapper() {
		Collection<PedidoProductoMapper> coleccion = pedidoProductoRepository.findAllMapper();
        if (coleccion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coleccion, HttpStatus.OK);
	}
}


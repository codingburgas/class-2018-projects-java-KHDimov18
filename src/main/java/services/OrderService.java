package services;

import models.Order;
import models.Product;
import repositories.OrderRepository;

import java.util.Date;
import java.util.List;

public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Boolean addOrder(Long customerId, String deliveryAddress, Date shipmentDate, List<Product> products) {
        Order order = new Order(0L, customerId, deliveryAddress, shipmentDate);
        Long orderId = orderRepository.addOrder(order);
        if(orderId!=-1L) {
            //order created successfully, add products to it
            for (Product product: products) {
                orderRepository.addToOrder(orderId, product.getProductId(), 1.0);
            }

            return true;
        }else{
            return false;
        }
    }

    public Boolean updateOrder(Long orderId, String deliveryAddress, Date shipmentDate) {
        Order order = orderRepository.getOrderById(orderId);

        if(order!=null) {
            order.setDeliveryAddress(deliveryAddress);
            order.setShipmentDate(shipmentDate);
            return orderRepository.updateOrder(order, orderId);
        } else {
            return false;
        }
    }

    public Boolean updateProductQuantityByOrder(Long orderId, Long productId, Double quantity) {
        Order order = orderRepository.getOrderById(orderId);

        if(order!=null)
        {
            return orderRepository.updateProductByOrder(orderId, productId, quantity);
        }
        else
        {
            return false;
        }
    }

    public Boolean removeProductByOrder(Long orderId, Long productId)
    {
        Order order = orderRepository.getOrderById(orderId);

        if(order!=null)
        {
            return orderRepository.removeProductFromOrder(orderId, productId);
        }
        else
        {
            return false;
        }
    }

    public Boolean deleteOrder(Long orderId) {
        return orderRepository.deleteOrder(orderId);
    }

    public List<Order> getOrders() {
        return orderRepository.getOrders();
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.getOrderById(orderId);
    }
}

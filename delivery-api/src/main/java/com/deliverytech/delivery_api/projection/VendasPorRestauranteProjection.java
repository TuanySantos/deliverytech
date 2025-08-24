package com.deliverytech.delivery_api.projection;

import java.math.BigDecimal;

public interface VendasPorRestauranteProjection {
    String getRestaurante();
    BigDecimal getTotalVendas();
}

import { useState, useEffect } from "react";
import { Card, Col, Row, Skeleton } from "antd";
import Meta from "antd/lib/card/Meta";
import { useQuery } from "react-query";
import { ShoppingCartOutlined } from "@ant-design/icons";
import { Product } from "../../types/product";

interface Error {
  message: string;
}

export function Marketplace() {
  const [productsInCart, setProductsInCart] = useState<Product[]>([]);

  const refreshProductsInCart = () => {
    const cart = localStorage.getItem("cart");
    if (cart) {
      setProductsInCart(
        JSON.parse(cart).map((cartItem: any) => cartItem.product)
      );
    }
  };

  useEffect(() => {
    refreshProductsInCart();
  }, []);

  const {
    isLoading,
    error,
    data: products,
  } = useQuery<Product[], Error>("repoData", () =>
    fetch(`${process.env.REACT_APP_API_URL}/marketplace`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    }).then((res) => res.json())
  );

  const addProductToCart = (product: Product) => {
    localStorage.setItem(
      "cart",
      JSON.stringify([
        ...JSON.parse(localStorage.getItem("cart") || "[]"),
        { product, quantity: 1 },
      ])
    );

    refreshProductsInCart();
  };

  if (isLoading)
    return (
      <Row gutter={[25, 40]}>
        {new Array(6).fill(0).map((_, i) => (
          <Col xs={32} md={16} lg={8} key={i}>
            <Card>
              <Skeleton active />
            </Card>
          </Col>
        ))}
      </Row>
    );

  if (error) return <div>Error: {error.message}</div>;

  return (
    <Row gutter={[25, 40]}>
      {products &&
        products.map((product) => (
          <Col xs={32} md={16} lg={8} key={product.id}>
            <Card
              cover={
                <img
                  alt="example"
                  src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
                />
              }
              actions={
                productsInCart.find((prod) => prod.id === product.id)
                  ? [<div>In cart</div>]
                  : [
                      <ShoppingCartOutlined
                        key="setting"
                        onClick={() => addProductToCart(product)}
                      />,
                    ]
              }
            >
              <Meta title={product.title} description={`$${product.price}`} />
            </Card>
          </Col>
        ))}
    </Row>
  );
}

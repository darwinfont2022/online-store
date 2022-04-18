import { useEffect, useState } from "react";
import { Cart as CartType, CartItem } from "../../types/cart";
import { Form, Table, Input, Space, Button } from "antd";
import { useMutation } from "react-query";

export function Cart() {
  const [cart, setCart] = useState<CartType>();
  const buy = useMutation(
    (cart: {
      items: { productId: number; quantity: number }[];
      accountNumber: string;
    }) => {
      return fetch(`${process.env.REACT_APP_API_URL}/payments`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify(cart),
      }).then((_) => {
        setCart(undefined);
        localStorage.removeItem("cart");
      });
    }
  );

  useEffect(() => {
    const cart = localStorage.getItem("cart");
    if (cart) {
      setCart(JSON.parse(cart));
    }
  }, [buy]);

  const onFinish = async (values: any) => {
    const { accountNumber } = values;
    buy.mutate({
      items:
        cart?.map((cartItem) => ({
          productId: cartItem.product.id,
          quantity: cartItem.quantity,
        })) || [],
      accountNumber,
    });
  };

  const onFinishFailed = () => {
    console.error("Submit failed!");
  };

  const removeProductFromCart = (productId: number) => {
    const newCart = cart?.filter(
      (cartItem) => cartItem.product.id !== productId
    );
    localStorage.setItem("cart", JSON.stringify(newCart));
    setCart(newCart);
  };

  const updateCartProductQuantity = (
    productId: number,
    newQuantity: number
  ) => {
    if (cart) {
      const newCartItems = [...cart];
      const index = newCartItems.findIndex(
        (item) => item.product.id === productId
      );
      newCartItems[index].quantity = newQuantity;
      setCart(newCartItems);

      localStorage.setItem("cart", JSON.stringify(newCartItems));
    }
  };

  if (!cart) {
    return <div>Cart is empty</div>;
  }

  const columns = [
    {
      title: "Title",
      dataIndex: "title",
    },
    {
      title: "Price",
      dataIndex: "price",
      render: (price: string) => <span>${price}</span>,
    },
    {
      title: "Quantity",
      dataIndex: "quantity",
      render: (quantity: number, cartItem: any) => (
        <Input
          type="number"
          value={quantity}
          min={1}
          onChange={(event) =>
            updateCartProductQuantity(cartItem.id, Number(event.target.value))
          }
          style={{ width: "70px" }}
        />
      ),
    },
    {
      title: "",
      dataIndex: "",
      render: (_: string, cartItem: any) => (
        <Button
          type="primary"
          danger
          onClick={() => removeProductFromCart(cartItem.id)}
        >
          Remove
        </Button>
      ),
    },
  ];

  const dataSource = cart.map(({ product, quantity }: CartItem) => ({
    id: product.id,
    title: product.title,
    price: product.price,
    quantity,
  }));

  return (
    <>
      <Form
        layout="vertical"
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
      >
        <Table
          dataSource={dataSource}
          columns={columns}
          pagination={false}
          style={{ marginBottom: "30px" }}
        />

        <h3 style={{ float: "right" }}>
          Total: $
          {cart.reduce((acc, cartItem) => {
            return acc + cartItem.product.price * cartItem.quantity;
          }, 0)}
        </h3>

        <Form.Item
          name="accountNumber"
          label="Account number"
          rules={[{ required: true }, { type: "string", len: 16 }]}
        >
          <Input placeholder="1234 5678 1234 5678" style={{ width: "200px" }} />
        </Form.Item>
        <Form.Item>
          <Space>
            <Button type="primary" htmlType="submit">
              Buy products
            </Button>
          </Space>
        </Form.Item>
      </Form>
    </>
  );
}

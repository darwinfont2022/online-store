import { useState } from "react";
import { Button, Form, Input, Select, Table } from "antd";
import { PlusCircleOutlined } from "@ant-design/icons";
import { useMutation, useQuery } from "react-query";
import { Product } from "../../types/product";

const { Option } = Select;

interface CatalogProductListProps {
  catalogId: number;
}

export function CatalogProductList({ catalogId }: CatalogProductListProps) {
  const [showModal, setShowModal] = useState(false);

  const createCatalogProductMutation = useMutation(
    ({ productId, fee }: { productId: number; fee: number }) => {
      return fetch(
        `${process.env.REACT_APP_API_URL}/catalogs/${catalogId}/products`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
          body: JSON.stringify([{ productId, fee }]),
        }
      ).then((res) => res.json());
    }
  );
  
  const removeProductMutation = useMutation((productId: number) => {
    return fetch(
      `${process.env.REACT_APP_API_URL}/catalogs/${catalogId}/products/${productId}`,
      {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      }
    ).then((res) => res.json());
  });

  const removeProduct = (productId: number) => {
    removeProductMutation.mutate(productId);
  };

  const columns = [
    {
      title: "Title",
      dataIndex: "title",
    },
    {
      title: "Price",
      dataIndex: "price",
    },
    {
      title: "Fee",
      dataIndex: "fee",
    },
    {
      title: "Options",
      dataIndex: "",
      key: "x",
      render: (_: string, product: Product) => (
        <Button type="primary" danger onClick={() => removeProduct(product.id)}>
          Delete
        </Button>
      ),
    },
  ];

  const {
    isLoading,
    error,
    data: products,
  } = useQuery<Product[], Error>(
    ["repoData", removeProductMutation, createCatalogProductMutation],
    () =>
      fetch(`${process.env.REACT_APP_API_URL}/catalogs/${catalogId}/products`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      }).then((res) => res.json())
  );

  const { data: finalProducts } = useQuery<Product[], Error>("repoData", () =>
    fetch(`${process.env.REACT_APP_API_URL}/products`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    }).then((res) => res.json())
  );

  const handleShowModal = () => {
    setShowModal(true);
  };

  const handleHideModal = () => {
    setShowModal(false);
  };

  const onFinish = (values: any) => {
    createCatalogProductMutation.mutate({
      productId: values.productId,
      fee: values.fee,
    });
    handleHideModal();
  };

  if (error) return <div>Error: {error.message}</div>;

  return (
    <>
      <Button
        type="primary"
        icon={<PlusCircleOutlined />}
        style={{ marginBottom: "30px" }}
        onClick={handleShowModal}
      >
        Add product
      </Button>
      {showModal && (
        <>
          <Form layout="vertical" onFinish={onFinish}>
            <Form.Item label="Product" name="productId" required>
              <Select
                showSearch
                placeholder="Select a person"
                optionFilterProp="children"
                onChange={() => {}}
                onSearch={() => {}}
              >
                {finalProducts &&
                  finalProducts.map((prod) => (
                    <Option value={prod.id}>
                      {prod.title} - ${prod.price}
                    </Option>
                  ))}
              </Select>
            </Form.Item>
            <Form.Item label="Fee" name="fee">
              <Input placeholder="ex: 10" type="number" />
            </Form.Item>
            <Form.Item>
              <Button onClick={handleHideModal}>Cancel</Button>{" "}
              <Button type="primary" htmlType="submit">
                Add
              </Button>
            </Form.Item>
          </Form>
        </>
      )}
      <Table loading={isLoading} columns={columns} dataSource={products} />
    </>
  );
}

import { Button, Form, Input, Table } from "antd";
import { PlusCircleOutlined } from "@ant-design/icons";
import { Link } from "react-router-dom";
import { useMutation, useQuery } from "react-query";
import { Catalog } from "../../types/catalog";
import { useState } from "react";

export function CatalogList() {
  const [showModal, setShowModal] = useState(false);

  const createCatalogMutation = useMutation(
    ({ title, fee }: { title: string; fee: number }) => {
      return fetch(`${process.env.REACT_APP_API_URL}/catalogs`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify({ title, fee }),
      }).then((res) => res.json());
    }
  );

  const removeCatalogMutation = useMutation((id: number) => {
    return fetch(`${process.env.REACT_APP_API_URL}/catalogs/${id}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    }).then((res) => res.json());
  });

  const removeCatalog = (catalogId: number) => {
    removeCatalogMutation.mutate(catalogId);
  };

  const columns = [
    {
      title: "Title",
      dataIndex: "title",
      render: (title: string, catalog: Catalog) => (
        <Link to={`/catalogs/${catalog.id}`}>{title}</Link>
      ),
    },
    {
      title: "Fee",
      dataIndex: "fee",
    },
    {
      title: "",
      dataIndex: "",
      render: (_: string, catalog: any) => (
        <Button type="primary" danger onClick={() => removeCatalog(catalog.id)}>
          Remove
        </Button>
      ),
    },
  ];

  const {
    isLoading,
    error,
    data: catalogs,
  } = useQuery<Catalog[], Error>(
    ["repoData", removeCatalogMutation, createCatalogMutation],
    () =>
      fetch(`${process.env.REACT_APP_API_URL}/catalogs`, {
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
    createCatalogMutation.mutate({
      title: values.title,
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
        Add Catalog
      </Button>
      {showModal && (
        <>
          <Form layout="vertical" onFinish={onFinish}>
            <Form.Item label="Title" name="title" required>
              <Input placeholder="ex: Holidays" />
            </Form.Item>
            <Form.Item label="Fee" name="fee">
              <Input placeholder="ex: 10" type="number" min="1" />
            </Form.Item>
            <Form.Item>
              <Button onClick={handleHideModal}>Cancel</Button>{" "}
              <Button type="primary" htmlType="submit">
                Create
              </Button>
            </Form.Item>
          </Form>
        </>
      )}
      <Table loading={isLoading} columns={columns} dataSource={catalogs} />
    </>
  );
}

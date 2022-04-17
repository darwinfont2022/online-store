import { Layout as AntLayout, Menu } from "antd";
import { BrowserRouter, Link } from "react-router-dom";
import { Routes } from "../Routes/Routes";

import "./Layout.css";

const { Header, Content, Footer } = AntLayout;

export function Layout() {
  const logout = () => {
    localStorage.removeItem("token");
    window.location.pathname = "/";
  };

  return (
    <BrowserRouter>
      <AntLayout className="layout">
        <Header>
          <div className="logo" />
          <Menu theme="dark" mode="horizontal">
            <Menu.Item key="marketplace">
              <Link to="/">Marketplace</Link>
            </Menu.Item>
            <Menu.Item key="catalogs">
              <Link to="/catalogs">Catalogs</Link>
            </Menu.Item>
            <Menu.Item key="cart">
              <Link to="/cart">Cart</Link>
            </Menu.Item>
            <Menu.Item key="logout">
              <Link to="#" onClick={logout}>
                Logout
              </Link>
            </Menu.Item>
          </Menu>
        </Header>
        <Content style={{ padding: "0 50px" }}>
          <div className="site-layout-content">
            <Routes />
          </div>
        </Content>
        <Footer style={{ textAlign: "center" }}>
          Online Store test @{new Date().getFullYear()}
        </Footer>
      </AntLayout>
    </BrowserRouter>
  );
}

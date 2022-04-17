import { Routes as ReactRouterRoutes, Route } from "react-router-dom";
import { Cart } from "../Cart/Cart";
import { CatalogDetails } from "../Catalogs/CatalogDetails";
import { CatalogList } from "../Catalogs/CatalogList";
import { Marketplace } from "../Marketplace/Marketplace";

export function Routes() {
  return (
    <ReactRouterRoutes>
      <Route path="/" element={<Marketplace />} />
      <Route path="/catalogs">
        <Route index element={<CatalogList />} />
        <Route path=":id" element={<CatalogDetails />} />
      </Route>
      <Route path="/cart" element={<Cart />} />
    </ReactRouterRoutes>
  );
}

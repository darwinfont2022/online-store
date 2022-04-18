import React from "react";
import { useParams } from "react-router-dom";
import { CatalogProductList } from "./CatalogProductList";

export function CatalogDetails() {
  const { id } = useParams();

  return (
    <div>
      <CatalogProductList catalogId={Number(id)} />
    </div>
  );
}

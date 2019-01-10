CREATE SCHEMA Production;

COMMENT ON SCHEMA Production IS 'Contains objects related to products, inventory, and manufacturing.';

CREATE TABLE Production.ProductReview(
    ProductReviewID SERIAL NOT NULL, -- int
    ProductID INT NOT NULL,
    ReviewerName varchar(50) NOT NULL,
    ReviewDate TIMESTAMP NOT NULL CONSTRAINT "DF_ProductReview_ReviewDate" DEFAULT (NOW()),
    EmailAddress varchar(50) NOT NULL,
    Rating INT NOT NULL,
    Comments varchar(3850),
    ModifiedDate TIMESTAMP NOT NULL CONSTRAINT "DF_ProductReview_ModifiedDate" DEFAULT (NOW()),
    CONSTRAINT "CK_ProductReview_Rating" CHECK (Rating BETWEEN 1 AND 5)
);

-- Add new column ReviewStatus for review process
ALTER TABLE Production.ProductReview ADD COLUMN ReviewStatus varchar(10);
COMMENT ON COLUMN Production.ProductReview.ReviewStatus IS 'Status of Product Review: NEW(newly created), APPROVED(approved for publication), DECLINED(contains bad words).';
  
COMMENT ON TABLE Production.ProductReview IS 'Customer reviews of products they have purchased.';
COMMENT ON COLUMN Production.ProductReview.ProductReviewID IS 'Primary key for ProductReview records.';
COMMENT ON COLUMN Production.ProductReview.ProductID IS 'Product identification number. Foreign key to Product.ProductID.';
COMMENT ON COLUMN Production.ProductReview.ReviewerName IS 'Name of the reviewer.';
COMMENT ON COLUMN Production.ProductReview.ReviewDate IS 'Date review was submitted.';
COMMENT ON COLUMN Production.ProductReview.EmailAddress IS 'Reviewer''s e-mail address.';
COMMENT ON COLUMN Production.ProductReview.Rating IS 'Product rating given by the reviewer. Scale is 1 to 5 with 5 as the highest rating.';
COMMENT ON COLUMN Production.ProductReview.Comments IS 'Reviewer''s comments';

ALTER TABLE Production.ProductReview ADD CONSTRAINT "PK_ProductReview_ProductReviewID" PRIMARY KEY(ProductReviewID);

CLUSTER Production.ProductReview USING "PK_ProductReview_ProductReviewID";
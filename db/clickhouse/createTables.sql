CREATE TABLE hyperledger.blocks
(
    `block`          UInt32,
    `block_tx_count` Int64,
    `block_hash`     String,
    `data_hash`      String,
    `previous_hash`  String,
    `datetime`       DateTime
) ENGINE = MergeTree ORDER BY block;

CREATE TABLE hyperledger.transactions
(
    `transaction_hash`      String,
    `chaincode_name`        String,
    `function_name`         String,
    `validation_code`       String,
    `block`                 UInt32,
    `payload_proposal_hash` String,
    `payload`               String,
    `write_set`             Array(String),
    `datetime`              DateTime
) ENGINE = MergeTree ORDER BY block;